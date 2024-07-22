package com.minit.logger;

import com.minit.Logger;

import javax.servlet.ServletException;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public abstract class LoggerBase implements Logger {

    protected int debug = 0;

    protected static final String info = "com.minit.logger.LoggerBase/1.0";

    protected int verbosity = ERROR;

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    @Override
    public String getInfo() {
        return (info);
    }

    @Override
    public int getVerbosity() {
        return this.verbosity;
    }

    @Override
    public void setVerbosity(int verbosity) {
        this.verbosity = verbosity;
    }

    public void setVerbosityLevel(String verbosity){
        if ("FATAL".equalsIgnoreCase(verbosity))
            this.verbosity = FATAL;
        else if ("ERROR".equalsIgnoreCase(verbosity))
            this.verbosity = ERROR;
        else if ("WARNING".equalsIgnoreCase(verbosity))
            this.verbosity = WARNING;
        else if ("INFORMATION".equalsIgnoreCase(verbosity))
            this.verbosity = INFORMATION;
        else if ("DEBUG".equalsIgnoreCase(verbosity))
            this.verbosity = DEBUG;
    }

    public abstract void log(String message);

    @Override
    public void log(Exception exception, String msg) {
        log(msg,exception);
    }

    @Override
    public void log(String message, Throwable exception) {
        CharArrayWriter buf = new CharArrayWriter();
        PrintWriter writer = new PrintWriter(buf);
        writer.println(message);
        exception.printStackTrace(writer);
        Throwable rootCase = null;
        if(exception instanceof ServletException){
            rootCase = ((ServletException) exception).getRootCause();
        }
        if (rootCase != null){
            writer.println("-------------------Root cause----------------");
            rootCase.printStackTrace(writer);
        }
        log(buf.toString());
    }

    @Override
    public void log(String message, int verbosity) {
        if (this.verbosity >= verbosity) log(message);
    }

    @Override
    public void log(String message, Throwable exception, int verbosity) {
        if (this.verbosity >= verbosity) log(message, exception);
    }
}
