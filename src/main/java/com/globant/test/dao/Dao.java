package com.globant.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gervasio.amy
 */
public class Dao {

    @Autowired
    protected Repository repo;

    @Cacheable(value = "defaultCache", key = "#key")
    public String getValue(String key) {
        return repo.get(key);
    }

    @CachePut(value = "defaultCache", key = "#key")
    public String setValue(String key, String value) {
        repo.put(key, value);
        return value;
    }

}
