package com.p3.lwa.searchengine.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DrillRepository {


    private final JdbcTemplate jdbcTemplate;

    public DrillRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getResults(String query) {
        return jdbcTemplate.queryForList(query);

    }

}
