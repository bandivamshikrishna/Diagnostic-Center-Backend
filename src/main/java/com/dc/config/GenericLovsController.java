package com.dc.config;

import com.dc.service.GenericLovsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lovs")
public class GenericLovsController {

    private final GenericLovsService genericLovsService;

    public GenericLovsController(GenericLovsService genericLovsService){
        this.genericLovsService = genericLovsService;
    }

    @GetMapping("/{filterType}") //search Filters
    private ResponseEntity<List<Map<String,String>>> getSearchFilters(@PathVariable(name = "filterType") String filterType){
        return new ResponseEntity<>(genericLovsService.getSearchFilters(filterType), HttpStatus.OK);
    }
}
