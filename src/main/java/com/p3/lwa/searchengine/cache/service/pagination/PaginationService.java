package com.p3.lwa.searchengine.cache.service.pagination;

import com.google.common.collect.Lists;
import com.p3.lwa.searchengine.bean.QueryResult;
import com.p3.lwa.searchengine.cache.CacheEngine;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@EnableAsync
public class PaginationService  {

    private final CacheEngine cacheEngine;

    public PaginationService(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Async
    public void saveCacheInBackground(List<Map<String, Object>> queryResultList, int pageSize, QueryResult queryResult) {
        String dataId = queryResult.getDataId().toString();
        List<Map<String, Object>> sizeList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(2);
        List<List<Map<String, Object>>> paginationValues = setPaginationValues(queryResultList, pageSize);
        int totalSize = paginationValues.size();
        map.put("totalSize",totalSize);
        sizeList.add(map);
        paginationValues.stream().forEach(values->{
            cacheEngine.setValuesIntoCacheDB(values,dataId+"_"+String.valueOf(atomicInteger.getAndIncrement()));
        });
        cacheEngine.setValuesIntoCacheDB(sizeList,"totalSize");
    }

    private List<List<Map<String, Object>>> setPaginationValues(List<Map<String, Object>> queryResultList, int pageSize) {
        List<List<Map<String, Object>>> paginationValuesList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(queryResultList)){
            paginationValuesList = Lists.partition(queryResultList,pageSize);
        }
       return paginationValuesList;
    }

    public void saveFirstValue(List<Map<String, Object>> firstList,QueryResult queryResult) {
        String dataId = queryResult.getDataId().toString()+"_1";
        cacheEngine.setValuesIntoCacheDB(firstList,dataId);
    }
}
