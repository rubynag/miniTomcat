package com.minit.core;

import com.minit.Container;
import com.minit.Context;
import com.minit.Wrapper;
import com.minit.connector.HttpRequestFacade;
import com.minit.connector.HttpResponseFacade;
import com.minit.connector.http.HttpConnector;
import com.minit.connector.http.HttpRequestImpl;
import com.minit.startup.Bootstrap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardContext extends ContainerBase implements Context {
    HttpConnector connector = null;
    Map<String, String> servletClsMap = new ConcurrentHashMap<>();
    Map<String, StandardWrapper> servletInstanceMap = new ConcurrentHashMap<>();

    public StandardContext() {
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Bootstrap.WEB_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public String getInfo() {
        return "Minit Servlet Context, vesion 0.1";
    }

    @Override
    public void addChild(Container child) {

    }

    public HttpConnector getConnector() {
        return connector;
    }

    public void setConnector(HttpConnector connector) {
        this.connector = connector;
    }


    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StandardWrapper standardWrapper = null;
        String uri = ((HttpRequestImpl)request).getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        String servletClassName = servletName;

        standardWrapper = servletInstanceMap.get(servletName);
        if (standardWrapper == null) {
            standardWrapper = new StandardWrapper(servletClassName, this);
            servletClsMap.put(servletName, servletClassName);
            servletInstanceMap.put(servletName, standardWrapper); //按照规范，创建新实例的时候需要调用init()
        }
        try {
            HttpServletRequest requestFacade = new HttpRequestFacade(request);
            HttpServletResponse responseFacade = new HttpResponseFacade(response);
            System.out.println("Call service()");

            standardWrapper.invoke(requestFacade, responseFacade);
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(String displayName) {

    }

    @Override
    public String getDocBase() {
        return null;
    }

    @Override
    public void setDocBase(String docBase) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public int getSessionTimeout() {
        return 0;
    }

    @Override
    public void setSessionTimeout(int timeout) {

    }

    @Override
    public String getWrapperClass() {
        return null;
    }

    @Override
    public void setWrapperClass(String wrapperClass) {

    }

    @Override
    public Wrapper createWrapper() {
        return null;
    }

    @Override
    public String findServletMapping(String pattern) {
        return null;
    }

    @Override
    public String[] findServletMappings() {
        return new String[0];
    }

    @Override
    public void reload() {

    }
}