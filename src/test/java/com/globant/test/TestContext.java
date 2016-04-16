package com.globant.test;

import com.globant.test.dao.EmbeddedMemcachedController;
import com.globant.test.dao.PoCRepository;
import com.globant.test.dao.Repository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Created by gervasio.amy
 */
@Configuration
public class TestContext {

    private static final String ADDRESS = "127.0.0.1:11212";

    @Bean
    public EmbeddedMemcachedController embeddedMemcachedController() {

        EmbeddedMemcachedController memcachedController = new EmbeddedMemcachedController();
        memcachedController.setAddress(ADDRESS);

        memcachedController.start();

        return memcachedController;
    }

    @Bean
    public Repository repo() {
        return Mockito.mock(Repository.class);
    }


    @Bean
    @DependsOn("embeddedMemcachedController")
    public Boolean memcachedStarted() {
        return Boolean.TRUE;
    }

}
