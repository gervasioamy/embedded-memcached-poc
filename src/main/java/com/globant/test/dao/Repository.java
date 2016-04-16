package com.globant.test.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Just a stupid "Repository" for PoC proposes
 *
 * @author gervasio.amy
 */
public interface Repository {

    String get(String key);

    String put(String key, String value);

}
