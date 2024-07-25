package com.minit.core;

import com.minit.ContainerEvent;
import com.minit.ContainerListener;
import com.minit.Request;
import com.minit.Response;
import com.minit.connector.http.HttpConnector;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class StandardHost extends ContainerBase{

    HttpConnector connector = null;

    Map<String,StandardContext> contextMap = null;

    private ArrayList<ContainerListenerDef> listenerDefs = new ArrayList<>();
    private ArrayList<ContainerListener> listeners = new ArrayList<>();

    public StandardHost(){
        super();
        pipeline.setBasic(new StandardHostValve());
        log("Host created.");
    }
    @Override
    public String getInfo() {
        return "Minit host, vesion 0.1";
    }

    public HttpConnector getConnector() {
        return connector;
    }
    public void setConnector(HttpConnector connector) {
        this.connector = connector;
    }

    public void invoke(Request request, Response response)
            throws IOException, ServletException {
        System.out.println("StandardHost invoke()");

        super.invoke(request, response);
    }

    public StandardContext getContext(String name){
        StandardContext context = contextMap.get(name);
        if(context == null){
            context = new StandardContext();
            context.setDocBase(name);
            context.setConnector(connector);
            WebappClassLoader classLoader = new WebappClassLoader();
            context.setLoader(classLoader);
            classLoader.start();

            this.contextMap.put(name, context);
        }
        return context;
    }

    public void start(){

    }

    public void addContainerListener(ContainerListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }
    public void removeContainerListener(ContainerListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void fireContainerEvent(String type, Object data) {
        if (listeners.size() < 1)
            return;
        ContainerEvent event = new ContainerEvent(this, type, data);
        ContainerListener list[] = new ContainerListener[0];
        synchronized (listeners) {
            list = (ContainerListener[]) listeners.toArray(list);
        }
        for (int i = 0; i < list.length; i++)
            ((ContainerListener) list[i]).containerEvent(event);

    }
    public void addListenerDef(ContainerListenerDef listenererDef) {
        synchronized (listenerDefs) {
            listenerDefs.add(listenererDef);
        }
    }

    public boolean listenerStart() {
        System.out.println("Listener Start.....");
        boolean ok = true;
        synchronized (listeners) {
            listeners.clear();
            Iterator<ContainerListenerDef> defs = listenerDefs.iterator();
            while (defs.hasNext()) {
                ContainerListenerDef def = defs.next();
                ContainerListener listener = null;
                try {
                    // Identify the class loader we will be using
                    String listenerClass = def.getListenerClass();
                    WebappClassLoader classLoader = null;
                    classLoader = this.getLoader();

                    ClassLoader oldCtxClassLoader =
                            Thread.currentThread().getContextClassLoader();

                    // Instantiate a new instance of this filter and return it
                    Class<?> clazz = classLoader.getClassLoader().loadClass(listenerClass);
                    listener = (ContainerListener) clazz.newInstance();

                    addContainerListener(listener);
                } catch (Throwable t) {
                    t.printStackTrace();
                    ok = false;
                }
            }
        }
        return (ok);
    }
}
