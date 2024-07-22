package com.minit.connector.http;

import com.minit.Request;
import com.minit.Response;
import com.minit.connector.http.HttpConnector;
import com.minit.connector.http.HttpRequestImpl;
import com.minit.connector.http.HttpResponseImpl;

import javax.servlet.ServletException;
import java.io.IOException;

public class ServletProcessor {
    private HttpConnector connector;

    public ServletProcessor(HttpConnector connector){
        this.connector = connector;
    }
    public void process(Request request, Response response) throws ServletException, IOException {
        this.connector.getContainer().invoke(request, response);
    }



}
