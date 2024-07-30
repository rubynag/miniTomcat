package com.minit.core;

import com.minit.Context;
import com.minit.Loader;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.*;

public class ApplicationFilterConfig implements FilterConfig {
    public ApplicationFilterConfig(Context context, FilterDef filterDef) throws ServletException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        this.context = context;
        setFilterDef(filterDef);
    }

    private Context context = null;

    private FilterDef filterDef = null;

    private Filter filter = null;

    @Override
    public String getFilterName() {
        return filterDef.getFilterName();
    }

    @Override
    public ServletContext getServletContext() {
        return this.context.getServletContext();
    }

    FilterDef getFilterDef() {
        return (this.filterDef);
    }

    @Override
    public String getInitParameter(String s) {
        Map<String, String> parameterMap = filterDef.getParameterMap();
        if (parameterMap == null) {
            return null;
        } else {
            return parameterMap.get(s);
        }
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        Map<String, String> parameterMap = filterDef.getParameterMap();
        if (parameterMap == null) {
            return Collections.enumeration(new ArrayList<String>());
        } else {
            return Collections.enumeration(parameterMap.keySet());
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("ApplicationFilterConfig[");
        sb.append("name=");
        sb.append(filterDef.getFilterName());
        sb.append(", filterClass=");
        sb.append(filterDef.getFilterClass());
        sb.append("]");
        return (sb.toString());
    }

    Filter getFilter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException {
        if(this.filter != null)
            return (this.filter);
        String filterClass = filterDef.getFilterClass();
        Loader classLoader = null;
        classLoader = context.getLoader();

        //获取当前线程的上下文类加载器
        ClassLoader oldCtxClassLoder = Thread.currentThread().getContextClassLoader();

        Class clazz = classLoader.getClassLoader().loadClass(filterClass);
        this.filter = (Filter)clazz.newInstance();
        filter.init(this);
        return this.filter;
    }

    void release(){
        if(this.filter != null)
            filter.destroy();
        this.filter = null;
    }

    void setFilterDef(FilterDef filterDef) throws ServletException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.filterDef = filterDef;
        if(filterDef == null) {
            if (this.filter != null)
                filter.destroy();
            this.filter = null;
        } else{
            Filter filter1 = getFilter();
        }
    }
}
