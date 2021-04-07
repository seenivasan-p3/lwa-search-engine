package com.p3.lwa.searchengine.repository;

import java.util.List;
import java.util.Map;

public interface IgniteInterface {

    void save(String serviceId, List<Map<String,Object>> list);

    List<Map<String,Object>> getAll(String serviceId);


}
