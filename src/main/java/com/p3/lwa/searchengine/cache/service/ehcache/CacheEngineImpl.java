package com.p3.lwa.searchengine.cache.service.ehcache;


import com.p3.lwa.searchengine.cache.CacheEngine;
import com.p3.lwa.searchengine.cache.EhCacheEngine;
import com.p3.lwa.searchengine.repository.EhCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Conditional(EhCacheEngine.class)
public class CacheEngineImpl implements CacheEngine {

    @Autowired
    EhCacheRepository ehCacheRepository;

    @Override
    public void setValuesIntoCacheDB(List<Map<String, Object>> queryResultList, String dataId) {
        ehCacheRepository.save(dataId,queryResultList);
    }

    @Override
    public List<Map<String, Object>> getCacheEngineResultBasedOnDataId(String dataId) {
        List<Map<String, Object>> resultList = ehCacheRepository.get(dataId);
        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            return new ArrayList<>();
        }
    }
}
