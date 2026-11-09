package com.dc.serviceImpl;

import com.dc.entity.PatientGenderEntity;
import com.dc.repository.GenericLovsRepository;
import com.dc.repository.PatientGenderRepository;
import com.dc.repository.PatientTitleRepository;
import com.dc.service.GenericLovsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenericLovsServiceImpl implements GenericLovsService {

    private final GenericLovsRepository genericLovsRepository;


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
