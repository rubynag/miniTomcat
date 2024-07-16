package com.minit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Session {

    public static final String SESSION_CREATED_EVENT = "createSession";
    public static final String SESSION_DESTROYED_EVENT = "destroySession";
    public long getCreationTime();
    public void setCreationTime(long time);
    public String getId();
    public void setId(String id);
    public String getInfo();
    public long getLastAccessedTime();
    public int getMaxInactiveInterval();
    public void setMaxInactiveInterval(int interval);
    public void setNew(boolean isNew);
    public HttpSession getSession();
    public void setValid(boolean isValid);
    public boolean isValid();
    public void access();
    public void expire();
    public void recycle();


}
