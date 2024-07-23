package com.minit.core;

import com.minit.connector.HttpRequestFacade;
import com.minit.connector.HttpResponseFacade;
import com.minit.connector.http.HttpRequestImpl;
import com.minit.connector.http.HttpResponseImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ApplicationFilterChain implements FilterChain {
    public ApplicationFilterChain(){
        super();
    }

    private ArrayList<ApplicationFilterConfig> filterConfigs = new ArrayList<>();

    private Iterator<ApplicationFilterConfig> iterator = null;

    private Servlet servlet = null;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        System.out.println("FilterChain doFilter()");
        internalDoFilter(servletRequest,servletResponse);
    }

    private void internalDoFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        if(this.iterator == null)
            this.iterator = this.filterConfigs.iterator();
        if(this.iterator.hasNext()){
            ApplicationFilterConfig filterConfig = this.iterator.next();
            Filter filter = null;
            try {
                filter = filterConfig.getFilter();
                System.out.println("FilterChain doFilter()");
                filter.doFilter(servletRequest,servletResponse,this);
            } catch (IOException e) {
                throw e;
            } catch (ServletException e) {
                throw e;
            } catch (RuntimeException e) {
                throw e;
            }catch (Throwable e){
                throw new ServletException("filterChain.filter", e);
            }
            return;
        }

        try {
            HttpServletRequest requestFacade = new HttpRequestFacade((HttpRequestImpl) servletRequest);
            HttpServletResponse responseFacade = new HttpResponseFacade((HttpResponseImpl) servletResponse);

            servlet.service(requestFacade, responseFacade);
        } catch (IOException e) {
            throw e;
        } catch (ServletException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new ServletException("filterChain.servlet", e);
        }
    }
    void addFilter(ApplicationFilterConfig filterConfig) {
        this.filterConfigs.add(filterConfig);
    }

    void release() {
        this.filterConfigs.clear();
        this.iterator = iterator;
        this.servlet = null;
    }

    void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }
}
