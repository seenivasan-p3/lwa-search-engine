package com.p3.lwa.searchengine.repository;

import com.p3.lwa.searchengine.exception.CacheNotFoundException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@RepositoryConfig(cacheName = "igniteCache")
//@Repository
@Component
public class IgniteResultRepository implements IgniteInterface {


    @Autowired
    private Ignite ignite;

    RandomStringUtils randomUtils = new RandomStringUtils();

    public Cache<String, List> getAlertsConfigCache() {
        return ignite.getOrCreateCache("Alerts");

    }

    @Override
    public void save(String dataId, List<Map<String, Object>> list) {
        getAlertsConfigCache().put(dataId,list);
    }

    @Override
    public List<Map<String, Object>> getAll(String dataId) {
        return Optional.ofNullable(getAlertsConfigCache().get(dataId))
                .orElseThrow(() -> new CacheNotFoundException("Cache not Found"));


//        List<Map<String, Object>> cacheList = new ArrayList<>();
//        cacheList = getAlertsConfigCache().get(dataId);
//        return cacheList;
    }
}
