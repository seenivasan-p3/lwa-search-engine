package com.p3.lwa.searchengine.cache;

import com.p3.lwa.searchengine.bean.QueryResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CacheEngine {

    void setValuesIntoCacheDB(List<Map<String, Object>> queryResultList, String dataId);

    List<Map<String, Object>> getCacheEngineResultBasedOnDataId(String dataId);


}

