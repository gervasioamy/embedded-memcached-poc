package com.globant.test.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic impl of {@link Repository} based on a Map. That's it
 * @author gervasio.amy
 *
 */
public class PoCRepository implements Repository {

    private Map<String, String> repo = new HashMap<String, String>();


    @Override
    public String get(String key) {
        return repo.get(key);
    }

    @Override
    public String put(String key, String value) {
        repo.put(key, value);
        return value;
    }
}
