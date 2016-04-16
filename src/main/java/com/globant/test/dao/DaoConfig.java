package com.globant.test.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gervasio.amy
 */
@Configuration
public class DaoConfig {

    //@Bean
    public Repository repo() {
        return new PoCRepository();
    }

}
