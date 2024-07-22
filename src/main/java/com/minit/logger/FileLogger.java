package com.minit.logger;

import com.minit.util.StringManager;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class FileLogger extends LoggerBase {

    private String date = "";

    private String directory = "";
    protected static final String info = "com.minit.logger.FileLogger/1.0";

    private String prefix = "minit.";

    private StringManager sm = StringManager.getManager(Constants.Package);

    private boolean started = false;
    private String suffix = ".log";

    private boolean timestamp = true;

    private PrintWriter writer = null;

    public String getDirectory() {
        return (directory);
    }

    public void setDirectory(String directory) {
        String oldDirectory = this.directory;
        this.directory = directory;
    }

    public String getPrefix() {
        return (prefix);
    }

    public void setPrefix(String prefix) {
        String oldPrefix = this.prefix;
        this.prefix = prefix;
    }

    public String getSuffix() {
        return (suffix);
    }

    public void setSuffix(String suffix) {
        String oldSuffix = this.suffix;
        this.suffix = suffix;
    }

    public boolean getTimestamp() {
        return (timestamp);
    }

    public void setTimestamp(boolean timestamp) {
        boolean oldTimestamp = this.timestamp;
        this.timestamp = timestamp;
    }

    @Override
    public void log(String message) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsString = ts.toString().substring(0,19);
        String tsDate = tsString.substring(0,10);
        if (!tsDate.equals(date)) {
            synchronized (this){
                if(!date.equals(tsDate)){
                    close();
                    date = tsDate;
                    open();
                }
            }
        }
        if(writer != null){
            if(timestamp){
                writer.println(tsString + " " + message);
            }else {
                writer.println(message);
            }
        }

    }

    private void close(){
        if(writer==null){
            return;
        }
        writer.flush();
        writer.close();
        writer = null;
        date = "";
    }

    private void open(){
        File dir = new File(directory);
        if(!dir.isAbsolute()){
            dir = new File(System.getProperty("catalina.base"),directory);
        }
        dir.mkdirs();
        try{
            String pathname = dir.getAbsolutePath()+File.separator+prefix+date+suffix;
            writer = new PrintWriter(new FileWriter(pathname,true),true);
        }catch(Exception e){
            writer = null;
        }

    }
}
