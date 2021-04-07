package com.p3.lwa.searchengine.cache.service.ignite;



import com.p3.lwa.searchengine.cache.CacheEngine;
import com.p3.lwa.searchengine.cache.IgniteCacheEngine;
import com.p3.lwa.searchengine.repository.IgniteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Conditional(IgniteCacheEngine.class)
public class CacheEngineImpl implements CacheEngine {

    @Autowired
    IgniteResultRepository igniteResultRepository;

    @Override
    public void setValuesIntoCacheDB(List<Map<String, Object>> queryResultList, String dataId) {
        igniteResultRepository.save(dataId,queryResultList);
    }

    @Override
    public List<Map<String, Object>> getCacheEngineResultBasedOnDataId(String dataId) {
        List<Map<String, Object>> all = igniteResultRepository.getAll(dataId);
        return all;
    }

}
