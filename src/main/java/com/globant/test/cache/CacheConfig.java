package com.globant.test.cache;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.aop.CacheBase;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.config.AddressProvider;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheClientFactory;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Main configuration for memcached. At this point it doesn't matter which memcached server will be user... if
 * a local instance, a mocked one or even the real(s)
 *
 * @author gervasio.amy
 */
@Configuration
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    private static final int EXPIRATION = 300;

    // FIXME this must be externalized... for PoC proposes it's not necessary
    private static final String ADDRESS = "127.0.0.1:11212";

    @Autowired
    private CacheBase cacheBase;

    @Bean
    public CacheManager cacheManager() throws Exception {
        MemcacheClientFactoryImpl cacheClientFactory = new MemcacheClientFactoryImpl();
        AddressProvider addressProvider = new DefaultAddressProvider(ADDRESS);
        com.google.code.ssm.providers.CacheConfiguration cacheConfiguration = new com.google.code.ssm.providers.CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);

        Cache object = null;
        try {
            object = cacheFactory().getObject();
        } catch (Exception e) {
            logger.error("There was an issue while building the cache object", e);
            throw e;
        }

        SSMCache ssmCache = new SSMCache(object, 10000, true); // third param allow remove all entries!!

        ArrayList<SSMCache> ssmCaches = new ArrayList<SSMCache>();
        ssmCaches.add(0, ssmCache);

        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        ssmCacheManager.setCaches(ssmCaches);

        return ssmCacheManager;
    }

    @Bean
    @DependsOn("memcachedStarted")
    public CacheFactory cacheFactory() {
        CacheFactory factory = new CacheFactory();
        factory.setCacheName("defaultCache");
        factory.setCacheClientFactory(new MemcacheClientFactoryImpl());

        factory.setAddressProvider(new DefaultAddressProvider(ADDRESS));

        CacheConfiguration config = new CacheConfiguration();
        config.setConsistentHashing(true);
        factory.setConfiguration(config);

        //factory.setDefaultSerializationType(SerializationType.JSON);

        return factory;
    }


}



