package com.ostojan.x360.controller;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ApiClientTest {

    @Test
    public void getInstanceReturnsNotNullObject() {
        assertNotEquals("ApiClient.getInstance() returned null object", null, ApiClient.getInstance());
    }

    @Test
    public void createMultipleApiClients() {
        try {
            tryCreateMultipleApiClients();
        } catch (NullPointerException e) {
            fail("ApiClient.getInstance() returned null object");
        }
    }

    private void tryCreateMultipleApiClients() throws NullPointerException{
        ApiClient apiClient1 = ApiClient.getInstance();
        ApiClient apiClient2 = ApiClient.getInstance();
        String errorMessage = "ApiClients are not equal";
        assertEquals(errorMessage, apiClient1, apiClient2);
        assertTrue(errorMessage, apiClient1 == apiClient2);
        assertEquals(errorMessage, apiClient1.hashCode(), apiClient2.hashCode());
    }

    @Test(expected = InvocationTargetException.class)
    public void createMultipleApiClientsUsingReflections() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ApiClient.getInstance();
        Class<ApiClient> classType = ApiClient.class;
        Constructor<ApiClient> constructor = classType.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
    
    @Test
    public void getApiClientInterfaceReturnsNotNullObject() {
        assertNotEquals("Returned object is null", null, ApiClient.getInstance().getApiClientInterface());
    }
}
