package com.p3.lwa.searchengine.cache.service.redis;


import com.p3.lwa.searchengine.cache.CacheEngine;
import com.p3.lwa.searchengine.cache.RedisCacheEngine;
import com.p3.lwa.searchengine.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Conditional(RedisCacheEngine.class)
public class CacheEngineImpl implements CacheEngine {

    @Autowired
    RedisRepository redisRepository;

    private final String REDIS_CACHE_KEY = "redisCacheKey";

    @Override
    public void setValuesIntoCacheDB(List<Map<String, Object>> queryResultList, String dataId) {
        redisRepository.save(REDIS_CACHE_KEY,dataId,queryResultList);
    }

    @Override
    public List<Map<String, Object>> getCacheEngineResultBasedOnDataId(String dataId) {
        return redisRepository.getValues(REDIS_CACHE_KEY,dataId);
    }
}
