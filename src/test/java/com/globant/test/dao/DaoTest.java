package com.globant.test.dao;

import com.globant.test.Application;
import com.globant.test.TestContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Just a simple test
 *
 * @author gervasio.amy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestContext.class, Application.class})
//@IntegrationTest({"memcached.port=11211"})
public class DaoTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private Dao dao;

    @Autowired
    private Repository mockedRepo;

    @Before
    public void setUp() {
        // all tests starts with a clean cache
        getCache().clear();
        // duo to we are using Mockito to create a mock (Repository) as a spring bean in the test context, then we need
        // to hard reset it by hand before every test. Mockito suggest to create mocks in test calses so it handles it,
        // but in cases like this it is allowed to hard reset mocks...
        reset(mockedRepo);
    }

    @Test
    public void givenAKeyIsNotCached_whenGetValue_DaoCallsRepo() {
        dao.getValue("someKey");
        verify(mockedRepo).get(anyString());
    }


    @Test
    public void givenNoCondition_WhenSetValue_DaoCallsRepoAndKeyKeepsCached() {
        dao.setValue("foo", "bar");
        assertNotNull("Key 'foo' should be cached", getCache().get("foo"));
        assertEquals("Key 'foo' value should be 'bar'", "bar", getCache().get("foo").get());
        verify(mockedRepo).put("foo", "bar");
    }


    @Test
    public void givenSetValue_whenGetValueForSameKey_DaoShouldNotCallRepo() {
        dao.setValue("foo", "bar");
        dao.getValue("foo");
        verify(mockedRepo, never()).get("foo");
    }


    private Cache getCache() {
        return cacheManager.getCache("defaultCache");
    }


}
