package com.globant.test;

import com.globant.test.dao.Dao;
import com.globant.test.dao.PoCRepository;
import com.globant.test.dao.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gervasio.amy
 */
@SpringBootApplication
@EnableCaching
@ImportResource("classpath:simplesm-context.xml") // need to import it , as legacy xml beans definition file
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Dao dao() {
        return new Dao();
    }

}
