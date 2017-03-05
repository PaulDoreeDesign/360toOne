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
    public void createMultipleApiClientInterfaces() {
        try {
            tryCreateMultipleApiClientsInterface();
        } catch (NullPointerException e) {
            fail("ApiClient.getApiClientInterface() returned null object");
        }
    }

    private void tryCreateMultipleApiClientsInterface() throws NullPointerException{
        ApiClientInterface apiClient1 = ApiClient.getApiClientInterface();
        ApiClientInterface apiClient2 = ApiClient.getApiClientInterface();
        String errorMessage = "ApiClientsInterface are not equal";
        assertTrue(errorMessage, apiClient1 == apiClient2);
        assertEquals(errorMessage, apiClient1.hashCode(), apiClient2.hashCode());
    }
    
    @Test
    public void getApiClientInterfaceReturnsNotNullObject() {
        assertNotEquals("Returned object is null", null, ApiClient.getApiClientInterface());
    }
}
