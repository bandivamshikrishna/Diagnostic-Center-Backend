package com.dc.serviceImpl;

import com.dc.repository.GenericLovsRepository;
import com.dc.service.GenericLovsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenericLovsServiceImpl implements GenericLovsService {

    private final GenericLovsRepository genericLovsRepository;

    public GenericLovsServiceImpl(GenericLovsRepository genericLovsRepository){
        this.genericLovsRepository = genericLovsRepository;
    }

    @Override
    public List<Map<String, String>> getSearchFilters(String filterType) {
        return genericLovsRepository.findByTypeOrderBySequenceAsc(filterType.toUpperCase()).stream().map(
                type->{
                    Map<String,String> map = new HashMap<>();
                    map.put("name", type.getCode());
                    map.put("value", type.getValue());
                    return map;
                }).toList();
    }
}
