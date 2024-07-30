package com.minit.core;

import com.minit.Container;
import com.minit.Loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class WebappLoader implements Loader {

    ClassLoader classLoader;
    ClassLoader parent;
    String path;
    String docbase;
    Container container;

    public WebappLoader(String docbase) {
        this.docbase = docbase;
    }

    public WebappLoader(String docbase, ClassLoader parent) {
        this.docbase = docbase;
        this.parent = parent;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setDocbase(String docbase) {
        this.docbase = docbase;
    }

    @Override
    public String getDocbase() {
        return docbase;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public String getInfo() {
        return "A simple loader";
    }

    @Override
    public void addRepository(String repository) {

    }

    @Override
    public String[] findRepositories() {
        return null;
    }

    @Override
    public void start() {
        System.out.println("Starting WebappLoader");

        try {
            URL[] urls = new URL[1];
            URLStreamHandler handler = null;
            File classPath = new File(System.getProperty("minit.base"));
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            if(docbase != null && !docbase.equals("")){
                repository = repository + docbase +File.separator;
            }
            repository = repository + "WEB-INF" + File.separator + "classes" + File.separator;
            urls[0] = new URL(null, repository, handler);
            System.out.println("Webapp classloader Repository : " + repository);
            classLoader = new WebappClassLoader(urls, parent);
        } catch (Exception e){

        }
    }

    @Override
    public void stop() {

    }
}
