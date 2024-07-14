package server;

import java.io.File;

public class HttpServer {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator + "webroot";

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        ServletContainer servletContainer = new ServletContainer();
        connector.setContainer(servletContainer);
        servletContainer.setConnector(connector);
        connector.start();
    }

}