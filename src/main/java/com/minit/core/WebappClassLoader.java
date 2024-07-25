package com.minit.core;

import com.minit.Container;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class WebappClassLoader {
    ClassLoader classLoader;
    String path;
    String docbase;
    Container container;

    public Container getContainer() {
        return container;
    }
    public void setContainer(Container container) {
        this.container = container;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getDocbase() {
        return docbase;
    }
    public void setDocbase(String docbase) {
        this.docbase = docbase;
    }

    public WebappClassLoader() {
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getInfo() {
        return "A simple loader";
    }

    public void addRepository(String repository) {
    }

    public String[] findRepositories() {
        return null;
    }

    public synchronized void start(){
        System.out.println("Starting WebappLoader");
        try {
            URL[] urls = new URL[1];
            URLStreamHandler handler = null;
            File classPath = new File(System.getProperty("minit.base"));
            String repository = (new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
            if (docbase!=null && !docbase.equals("")) {
                repository = repository + docbase + File.separator;
            }
            urls[0] = new URL(null,repository,handler);
            System.out.println("Webapp classloader Repository : "+repository);

            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public void stop() {
    }
}
