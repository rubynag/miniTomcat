package com.minit.startup;

import com.minit.Loader;
import com.minit.Logger;
import com.minit.connector.http.HttpConnector;
import com.minit.core.*;
import com.minit.logger.FileLogger;

import java.io.File;

public class Bootstrap {

    public static final String MINIT_HOME = System.getProperty("user.dir");
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator + "webapps";
    public static final int PORT = 8080;
    private static final int debug = 0;

    public static void main(String[] args) {
        if(debug >=1){
            log(".... startup ....");
        }

        System.setProperty("minit.home", MINIT_HOME);
        System.setProperty("minit.base", WEB_ROOT);

        HttpConnector connector = new HttpConnector();
        StandardHost container = new StandardHost();

        Loader loader = new CommonLoader();
        container.setLoader(loader);
        loader.start();

        connector.setContainer(container);
        container.setConnector(connector);

        container.start();
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