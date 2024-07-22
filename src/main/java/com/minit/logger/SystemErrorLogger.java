package com.minit.logger;

public class SystemErrorLogger extends LoggerBase{
    protected static final String info = "com.minit.logger.SystemErrorLogger/1.0";
    @Override
    public void log(String message) {
        System.err.println(message);
    }
}
