package com.minit.startup;

import com.minit.Logger;
import com.minit.connector.http.HttpConnector;
import com.minit.core.FilterDef;
import com.minit.core.FilterMap;
import com.minit.core.StandardContext;
import com.minit.logger.FileLogger;

import java.io.File;

public class Bootstrap {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator + "webroot";
    private static final int debug = 0;

    public static void main(String[] args) {
        if(debug >=1){
            log(".... startup ....");
        }
        HttpConnector connector = new HttpConnector();
        StandardContext container = new StandardContext();
        connector.setContainer(container);
        container.setConnector(connector);
        Logger logger = new FileLogger();
        container.setLogger(logger);

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("TestFilter");
        filterDef.setFilterClass("test.TestFilter");
        container.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("TestFilter");
        filterMap.setURLPattern("/*");
        container.addFilterMap(filterMap);

        container.filterStart();
        connector.start();
    }

    private static void log(String msg){
        System.out.println("Bootstrap: ");
        System.out.println(msg);
    }

    private static void log(String msg,Throwable e){
        log(msg);
        e.printStackTrace(System.out);
    }

}