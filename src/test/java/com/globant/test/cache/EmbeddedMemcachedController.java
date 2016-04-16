package com.globant.test.cache;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;

/**
 * This is the mocked memcached "server". It's just a daemon running on specified host and port
 *
 * @see MemCacheDaemon
 * @author gervasio.amy
 */
public class EmbeddedMemcachedController {

    private static int MAX_ITEMS = 5000000;
    // This is purely for unit tests.  We shouldn't be storing huge chunks of data
    private static int MAX_BYTES = 1024 * 1024 * 2;  // 2MB should be enough for anyone  (also, this gets allocated on startup every time, so keep it low)

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedMemcachedController.class);


    // Embeddable backend Memcached daemon
    private MemCacheDaemon<LocalCacheElement> memcached;

    private Integer port;
    private String host;
    private Boolean useBinary = false;


    public void start() {
        Assert.notNull(host, "host must be specified");
        Assert.notNull(port, "port must be specified");
        if (memcached != null) {
            return;
        }

        memcached = new MemCacheDaemon<LocalCacheElement>();

        CacheStorage<Key, LocalCacheElement> storage =
                ConcurrentLinkedHashMap.create(ConcurrentLinkedHashMap.EvictionPolicy.LRU, MAX_ITEMS, MAX_BYTES);

        memcached.setCache(new CacheImpl(storage));
        memcached.setBinary(useBinary);
        memcached.setAddr(new InetSocketAddress(host, port));
        //daemon.setIdleTime(idle);
        memcached.setVerbose(false);
        memcached.start();
        //System.out.println("========== Started embedded Memcached on port " + port );
        logger.info("==== Started embedded Memcached on port {}", port);
    }

    public void stop() {
        if (memcached != null) {
            try {
                memcached.stop();
            }
            catch (Exception e) {
                logger.warn("Problem shutting down embedded Memcached instance.", e);
            }
        }
    }

    public Integer getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public void setAddress(String address) {
        String[] parts = address.split(":");
        host = parts[0];
        port = Integer.parseInt(parts[1]);
    }


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getUseBinary() {
        return useBinary;
    }

    public void setUseBinary(Boolean useBinary) {
        this.useBinary = useBinary;
    }
}
