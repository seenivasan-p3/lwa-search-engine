package com.p3.lwa.searchengine.repository;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EhCacheRepository {


    @Autowired
    CacheManager cacheManager;



    public void save(String dataId, List<Map<String,Object>> queryResult){
        Cache cache = cacheManager.getCache("myCacheName");
        cache.put(new Element(dataId,queryResult));
    }

    public List<Map<String,Object>> get(String dataId){
        Cache cache = cacheManager.getCache("myCacheName");
        Element element = cache.get(dataId);
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) element.getObjectValue();
        if(!resultList.isEmpty()){
            return resultList;
        } else{
            return new ArrayList<>();
        }
    }

}
