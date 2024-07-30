package com.minit.core;

import com.minit.Container;
import com.minit.Loader;

import java.io.File;
import java.net.URL;
import java.net.URLStreamHandler;

public class CommonLoader implements Loader {

    ClassLoader classLoader;
    ClassLoader parent;

    String path;
    String docbase;
    Container container;

    public CommonLoader(){
    }
    public CommonLoader(ClassLoader parent){
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
        return new String[0];
    }

    @Override
    public void start() {
        System.out.println("Starting Common Loader, docbase: " + docbase);
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(System.getProperty("minit.home"));
            String repository = (new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
            repository = repository+"lib"+File.separator;

            urls[0] = new URL(null,repository,streamHandler);
            System.out.println("Common classloader Repository : "+repository);
            classLoader = new CommonClassLoader(urls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }
}
