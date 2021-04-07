package com.p3.lwa.searchengine.controller;



import com.p3.lwa.searchengine.bean.QueryResult;
import com.p3.lwa.searchengine.search.SearchEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/search")
public class SearchEngineController {

    private final SearchEngineService searchEngineService;

    public SearchEngineController(final SearchEngineService searchEngineService) {
        this.searchEngineService = searchEngineService;
    }

    @PostMapping
    public QueryResult getSearch(@RequestBody String query) {
        return searchEngineService.getSearchResults(query);
    }

    @GetMapping
    public QueryResult getSearchResults(@RequestParam (name="dataId") UUID dataId, @RequestParam (name = "page") int pageNumber, @RequestParam (name = "size")int pageSize) {
        return searchEngineService.getResultsFromCache(dataId,pageNumber,pageSize);
    }

}
