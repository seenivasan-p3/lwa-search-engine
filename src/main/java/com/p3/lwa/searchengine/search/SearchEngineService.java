package com.p3.lwa.searchengine.search;


import com.p3.lwa.searchengine.bean.QueryResult;

import java.util.UUID;


public interface SearchEngineService {

    public QueryResult getSearchResults(String query);
    public QueryResult getResultsFromCache(UUID dataId,int pageNumber,int pageSize);

}

