package com.p3.lwa.searchengine.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class RedisRepository {

    private final HashOperations hashOperations;

    private final RedisTemplate redisTemplate;


    public RedisRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();

    }

    public void saveSize(String redisKey, String dataId,int size) {
        hashOperations.put(redisKey,dataId,size);
    }

    public void save(String redisKey, String dataId, List<Map<String, Object>> queryResultList) {
        hashOperations.put(redisKey,dataId,queryResultList);
    }


    public Map<String,Object> get(String testKey) {
        Map<String,Object> values = hashOperations.entries(testKey);
        return values;
    }

    public void delete(String testKey){
        redisTemplate.delete(testKey);
    }

    public List<Map<String,Object>> getValues(String redisKey,String dataId) {
        List<Map<String,Object>> cacheResult = (List<Map<String, Object>>) hashOperations.get(redisKey, dataId);
        return cacheResult;
    }

    public int getCacheSize(String redisKey,String dataId) {
       int cacheResult = (int) hashOperations.get(redisKey, dataId);
       return cacheResult;
    }

}
