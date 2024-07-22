package com.minit.core;

import com.minit.*;

import javax.servlet.ServletException;
import java.io.IOException;

public class StandardPipeline implements Pipeline {
    public StandardPipeline(){this(null);}

    public StandardPipeline(Container container) {
        super();
        setContainer(container);
    }

    protected Valve basic = null;

    protected Container container = null;

    protected int debug = 0;
    protected String info = "com.minit.core.StandardPipeline/0.1";

    private Valve valves[] = new Valve[0];

    public String getInfo(){
        return this.info;
    }

    public Container getContainer(){ return this.container;}

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public Valve getBasic() {
        return this.basic;
    }

    @Override
    public void setBasic(Valve valve) {
        // Change components if necessary
        Valve oldBasic = this.basic;
        if (oldBasic == valve)
            return;

        // Start the new component if necessary
        if (valve == null)
            return;
        valve.setContainer(container);
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        synchronized (valves){
            Valve result[] = new Valve[valves.length + 1];
            System.arraycopy(valves,0,result,0,valves.length);
            valve.setContainer(container);
            result[valves.length] = valve;
            valves = result;
        }
    }

    @Override
    public Valve[] getValves() {
        if(basic == null){
            return(valves);
        }
        synchronized (valves){
            Valve result[] = new Valve[valves.length + 1];
            System.arraycopy(valves,0,result,0,valves.length);
            result[valves.length] = basic;
            valves = result;
        }
        return new Valve[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        System.out.println("StandardPipeline invoke()");
        // Invoke the first Valve in this pipeline for this request
        (new StandardPipelineValveContext()).invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {
        synchronized (valves){
            int j = -1;
            for (int i = 0; i < valves.length; i++) {
                if(valves[i] == valve){
                    j = i;
                    break;
                }
            }

            if(j < 0)
                return;
            valve.setContainer(null);

            Valve result[] = new Valve[valves.length - 1];
            int n = 0;
            for (int i = 0; i < valves.length; i++) {
                if(i == j)
                    continue;
                result[n++] = valves[i];

            }
            valves = result;
        }
    }

    protected void log(String msg){
        Logger logger = null;
        if(container!=null){
            logger = container.getLogger();
        }
        if(logger!=null){
            logger.log("StandardPipeline[" + container.getName() + "]: " +
                    msg);
        }else {
            System.out.println("StandardPipeline[" + container.getName() + "]: " +
                    msg);
        }
    }

    protected void log(String message,Throwable throwable){
        Logger logger = null;
        if (container != null)
            logger = container.getLogger();
        if (logger != null)
            logger.log("StandardPipeline[" + container.getName() + "]: " +
                    message, throwable);
        else {
            System.out.println("StandardPipeline[" + container.getName() +
                    "]: " + message);
            throwable.printStackTrace(System.out);
        }
    }

    protected class StandardPipelineValveContext implements ValveContext {

        protected int stage = 0;
        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            System.out.println("StandardPipelineValveContext invokeNext()");

            int subscript = stage;
            stage = stage + 1;

            if(subscript < valves.length){
                valves[subscript].invoke(request,response,this);
            } else if (subscript == valves.length && basic != null) {
                basic.invoke(request,response,this);
            }else {
                System.out.println("StandardPipelineValveContext invokeNext() end");
            }

        }
    }

}
