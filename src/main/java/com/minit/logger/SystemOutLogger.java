package com.minit.logger;

public class SystemOutLogger extends LoggerBase{
    protected static final String info = "com.minit.logger.SystemOutLogger/1.0";
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
