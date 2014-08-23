package com.owner.service;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;



public class Client {
    private static HttpClient instance = null;

    private Client() {

    }

    public static HttpClient getInstance() {
        if (instance == null) {
            return instance = new DefaultHttpClient();
        } else {
            return instance;
        }
    }
}
