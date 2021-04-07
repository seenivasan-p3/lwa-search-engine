package com.p3.lwa.searchengine.search;


import com.google.common.collect.Lists;
import com.p3.lwa.searchengine.bean.QueryResult;
import com.p3.lwa.searchengine.cache.CacheEngine;
import com.p3.lwa.searchengine.cache.service.pagination.PaginationService;
import com.p3.lwa.searchengine.controller.SearchEngineController;
import com.p3.lwa.searchengine.exception.CacheNotFoundException;
import com.p3.lwa.searchengine.repository.DrillRepository;
import com.p3.lwa.searchengine.repository.RedisRepository;
import oadd.org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@EnableCaching
public class SearchEngineServiceImpl implements Serializable, SearchEngineService {
        private final DrillRepository drillRepository;
        private final CacheEngine cacheEngine;
        private final RedisRepository redisRepository;
        private final PaginationService paginationService;

    public SearchEngineServiceImpl(DrillRepository drillRepository, CacheEngine cacheEngine, RedisRepository redisRepository, PaginationService paginationService) {
        this.drillRepository = drillRepository;
        this.cacheEngine = cacheEngine;
        this.redisRepository = redisRepository;
        this.paginationService = paginationService;
    }


    public QueryResult getSearchResults(String query)  {
        int pageSize = 10;
        UUID dataId = UUID.randomUUID();
        QueryResult queryResult = new QueryResult();
        List<Map<String, Object>> queryResultList = drillRepository.getResults(query);
        List<Map<String, Object>> firstList = queryResultList.subList(0,pageSize);
        queryResult.setQuery(query);
        queryResult.setDataId(dataId);
        queryResult.setPage(firstList);
        paginationService.saveFirstValue(firstList,queryResult);
        paginationService.saveCacheInBackground(queryResultList.subList(pageSize, queryResultList.size()), pageSize, queryResult);
        return queryResult;
    }


    public QueryResult getResultsFromCache(UUID dataId,int pageNumber,int pageSize) {
        QueryResult queryResult = new QueryResult();
        List<Map<String, Object>> cacheResults = new ArrayList<>();
        List<Map<String, Object>> sizeResult  = cacheEngine.getCacheEngineResultBasedOnDataId("totalSize");
        int totalSizeInt = 0;
        if(!sizeResult.isEmpty()){
            Object totalSize = sizeResult.get(0).get("totalSize");
            totalSizeInt = Integer.valueOf(String.valueOf(totalSize));
        }
        String cacheDataId = dataId.toString()+"_"+pageNumber;
        int nextPageRestriction = (pageNumber + pageSize/10)-1;
        if(pageNumber<=totalSizeInt && totalSizeInt>=nextPageRestriction){
            if(pageSize == 10){
                cacheResults = cacheEngine.getCacheEngineResultBasedOnDataId(cacheDataId);
                if(cacheResults.isEmpty()){
                    throw new CacheNotFoundException("Cache Not Found");
                }
                queryResult.setPage(cacheResults);
            } else{
                setPagesForCustomPageSize(pageNumber,pageSize,queryResult,dataId);
            }
        } else {
            throw new CacheNotFoundException("Cache Not Found");
        }
        setLinks(nextPageRestriction,totalSizeInt,queryResult,dataId,pageNumber,pageSize);
        queryResult.setDataId(dataId);
        return queryResult;
    }

    private void setPagesForCustomPageSize(int pageNumber, int pageSize, QueryResult queryResult,UUID dataId) {
        List<List<Map<String, Object>>> combinedList = new ArrayList<>();
        int numberOfIteration = pageSize/10;
        int j=1;
        for(int i=pageNumber;j<=numberOfIteration;i++){
            String cacheId = dataId.toString()+"_"+i;
            List<Map<String, Object>> cacheEngineResultBasedOnDataId = cacheEngine.getCacheEngineResultBasedOnDataId(cacheId);
            combinedList.add(cacheEngineResultBasedOnDataId);
            j++;
        }
        List<Map<String, Object>> finalCacheResults = setSingleList(combinedList);

        if(finalCacheResults.isEmpty()){
            throw new CacheNotFoundException("Cache Not Found");
        }
        queryResult.setPage(finalCacheResults);
    }

    private List<Map<String, Object>> setSingleList(List<List<Map<String, Object>>> combinedList) {
        List<Map<String, Object>> singleList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(combinedList)){
             combinedList.stream().map(list ->{
                list.stream().forEach(values->{
                    singleList.add(values);
                });
                return singleList;
            }).collect(Collectors.toList());
        }
        return singleList;
    }

    private void setLinks(int nextPageRestriction,int totalSizeInt,QueryResult queryResult,UUID dataId, int pageNumber, int pageSize) {
        Class<SearchEngineController> searchEngineControllerClass = SearchEngineController.class;
        queryResult.add(linkTo(methodOn(searchEngineControllerClass).getSearchResults(dataId,pageNumber,pageSize)).withSelfRel());
        if(totalSizeInt > nextPageRestriction){
            queryResult.add(linkTo(methodOn(searchEngineControllerClass).getSearchResults(dataId,pageNumber+1,pageSize)).withRel("next"));
        }
        if(pageNumber>1){
            queryResult.add(linkTo(methodOn(searchEngineControllerClass).getSearchResults(dataId,pageNumber-1,pageSize)).withRel("previous"));
        }
    }
}

