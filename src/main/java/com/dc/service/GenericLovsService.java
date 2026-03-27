package com.dc.service;

import java.util.List;
import java.util.Map;

public interface GenericLovsService {
    List<Map<String,String>> getSearchFilters(String filterType);
}
