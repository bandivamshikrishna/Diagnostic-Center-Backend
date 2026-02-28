package com.dc.utils;

import com.dc.entity.APILoggingEntity;
import com.dc.enums.MethodTypeEnum;
import com.dc.repository.APILoggingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class APILoggingFilter extends OncePerRequestFilter {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final APILoggingRepository apiLoggingRepository;
    public static final String[] headers = {"Content-Type", "Authorization", "Accept", "Origin"};
    public static final String[] sensitiveParams = {"Authorization", "password"};
    static LocalDateTime requestDateTime;
    static LocalDateTime responseDateTime;
    static String createdBy = "anonymousUser";
    static String status;
    static String uuid;
    static MethodTypeEnum methodType;

    public APILoggingFilter(APILoggingRepository apiLoggingRepository){
        this.apiLoggingRepository = apiLoggingRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String uuid = request.getHeader("uuid");

       //insert request details
        requestDateTime = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated())
            createdBy = authentication.getName();

        APILoggingEntity apiLoggingEntity = new APILoggingEntity();
        apiLoggingEntity.setUuid(uuid);
        apiLoggingEntity.setRequestDateTime(requestDateTime);
        apiLoggingEntity.setCreatedDateTime(LocalDateTime.now());
        apiLoggingEntity.setCreatedBy(createdBy);
        apiLoggingEntity.setStatus("0");
        methodType = MethodTypeEnum.valueOf(request.getMethod());
        apiLoggingEntity.setMethodType(methodType);
        apiLoggingRepository.save(apiLoggingEntity);

        try {
            filterChain.doFilter(requestWrapper,responseWrapper);
        }
        finally {
            responseDateTime = LocalDateTime.now();
            apiLoggingEntity.setRequest(getJson(requestWrapper));
            apiLoggingEntity.setResponse(getJson(responseWrapper));
            apiLoggingEntity.setResponseDateTime(responseDateTime);
            apiLoggingEntity.setStatus(status);
            apiLoggingRepository.save(apiLoggingEntity);

            responseWrapper.copyBodyToResponse();
        }
    }

    public String getJson(Object object){
        String classType = object.getClass().getSimpleName();
        if(classType.equalsIgnoreCase("contentcachingrequestwrapper")){
            ContentCachingRequestWrapper request = (ContentCachingRequestWrapper) object;
            ObjectNode jsonObject = objectMapper.createObjectNode();

            jsonObject.put("method", request.getMethod());
            jsonObject.put("URL", request.getRequestURI());
            jsonObject.put("query", request.getQueryString());


            //headers
            ObjectNode headerNode = objectMapper.createObjectNode();
            for (String header : headers){
                String headerVal = request.getHeader(header);
                if(headerVal != null){
                    if(isSensitive(header)){
                        headerVal = "***";
                    }
                    headerNode.put(header, headerVal);
                }
            }

            jsonObject.put("headers", headerNode);

            //body
            byte[] content = request.getContentAsByteArray();
            if(content.length > 0){

                String body = new String(content, StandardCharsets.UTF_8);
                try {
                    ObjectNode bodyNode = (ObjectNode) objectMapper.readTree(body);

                    for (String s : sensitiveParams){
                        if(bodyNode.has(s)) {
                            bodyNode.put(s, "***");
                        }
                    }

                    jsonObject.put("body", bodyNode);
                }
                catch (Exception e){
                    jsonObject.put("body", body);
                }
            }

            return jsonObject.toPrettyString();
        }
        else if(classType.equalsIgnoreCase("contentcachingresponsewrapper")) {
            ContentCachingResponseWrapper response = (ContentCachingResponseWrapper) object;
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("status", response.getStatus());
            status = String.valueOf(response.getStatus());

            byte[] content = response.getContentAsByteArray();
            if(content.length >0){
                String body = new String(content, StandardCharsets.UTF_8);
                try {
                    jsonObject.put("body", objectMapper.readTree(body));
                }
                catch (Exception e){
                    jsonObject.put("body", body);
                }
            }
            return jsonObject.toPrettyString();
        }
        return "";
    }

    boolean isSensitive(String data){
        for (String param : sensitiveParams){
            if(data.equalsIgnoreCase(param))
                return true;
        }
        return false;
    }
}
