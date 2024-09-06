package com.minit.startup;

import com.minit.Loader;
import com.minit.Logger;
import com.minit.connector.http.HttpConnector;
import com.minit.core.*;
import com.minit.logger.FileLogger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class Bootstrap {

    public static final String MINIT_HOME = System.getProperty("user.dir");
    public static String WEB_ROOT =
            System.getProperty("user.dir") ;
    public static int PORT = 8080;
    private static int debug = 0;

    public static void main(String[] args) {
        if(debug >=1){
            log(".... startup ....");
        }

        String file = MINIT_HOME + File.separator + "conf" +File.separator + "server.xml";
        SAXReader reader = new SAXReader();
        Document document;
        try{
            document = reader.read(file);
            Element root = document.getRootElement();

            Element connectorElement = root.element("Connector");
            Attribute portAttribute = connectorElement.attribute("port");

            PORT = Integer.parseInt(portAttribute.getText());
            Element hostelement = root.element("Host");
            Attribute appbaseAttribute = hostelement.attribute("appBase");
            WEB_ROOT = WEB_ROOT + File.separator + appbaseAttribute.getText();
        }catch (Exception e){
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