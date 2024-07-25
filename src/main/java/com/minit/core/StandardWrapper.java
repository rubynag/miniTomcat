package com.minit.core;

import com.minit.Container;
import com.minit.Request;
import com.minit.Response;
import com.minit.Wrapper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class StandardWrapper extends ContainerBase implements Wrapper {
    private Servlet instance = null;

    private String servletClass;

    public StandardWrapper(String servletClass, StandardContext parent) {
        super();
        pipeline.setBasic(new StandardWrapperValve());

        this.servletClass = servletClass;
        this.parent = parent;
        try{
            loadServlet();
        }catch (ServletException e){
            e.printStackTrace();
        }

    }


    public String getServletClass() {
        return servletClass;
    }
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public Servlet getServlet(){
        return this.instance;
    }

    public Servlet loadServlet() throws ServletException{
        if (instance != null)
            return instance;
        Servlet servlet = null;
        String actualClass = servletClass;
        if (actualClass == null)
            throw new ServletException("servlet class not been specified");

        WebappClassLoader classLoader = getLoader();
        Class classClass = null;
        try{
            if(actualClass != null){
                classClass = classLoader.getClassLoader().loadClass(actualClass);
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Servlet class not found:"+actualClass);
        }
        try {
            servlet = (Servlet) classClass.newInstance();
        }
        catch (Throwable e) {
            throw new ServletException("Failed to instantiate servlet");
        }

        try {
            servlet.init(null);
        }
        catch (Throwable f) {
            throw new ServletException("Failed initialize servlet.");
        }
        instance =servlet;
        return servlet;
    }
    public void invoke(Request request, Response response)
            throws IOException, ServletException {
        System.out.println("StandardWrapper invoke()");

        super.invoke(request, response);
    }
    @Override
    public int getLoadOnStartup() {
        return 0;
    }

    @Override
    public void setLoadOnStartup(int value) {

    }
    @Override
    public String getInfo() {
        return "Minit Servlet Wrapper, version 0.1";
    }

    public void addChild(Container child) {}

    public Container findChild(String name) {return null;}
    public Container[] findChildren() {return null;}
    public void removeChild(Container child) {}

    @Override
    public void addInitParameter(String name, String value) {

    }

    @Override
    public Servlet allocate() throws ServletException {
        return null;
    }

    @Override
    public String findInitParameter(String name) {
        return null;
    }

    @Override
    public String[] findInitParameters() {
        return new String[0];
    }

    @Override
    public void load() throws ServletException {

    }

    @Override
    public void removeInitParameter(String name) {

    }


}
