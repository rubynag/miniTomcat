package com.minit;

public interface Loader {
    public Container getContainer();
    public void setContainer(Container container);
    public String getPath();
    public void setPath(String path);
    public void setDocbase(String docbase);
    public String getDocbase();
    public ClassLoader getClassLoader();
    public String getInfo();
    public void addRepository(String repository);
    public String[] findRepositories();
    public void start();
    public void stop();
}
