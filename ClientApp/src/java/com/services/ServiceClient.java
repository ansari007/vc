/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:TestService [/testService]<br>
 * USAGE:
 * <pre>
 *        ServiceClient client = new ServiceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author mohamad
 */
public class ServiceClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/WebServicesApplicationMaven/resources";

    public ServiceClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("testService");
    }

    public String hello() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("hello");
        return resource.request().get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
