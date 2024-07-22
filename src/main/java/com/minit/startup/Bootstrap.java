package com.minit.startup;

import com.minit.Logger;
import com.minit.connector.http.HttpConnector;
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
        StandardContext standardContext = new StandardContext();
        connector.setContainer(standardContext);
        standardContext.setConnector(connector);
        Logger logger = new FileLogger();
        standardContext.setLogger(logger);
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