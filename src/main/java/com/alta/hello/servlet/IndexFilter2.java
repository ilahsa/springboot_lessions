package com.alta.hello.servlet;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by baiba on 2018-09-14.
 */
@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "indexFilter2",asyncSupported=true )
public class IndexFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("filter doFilter.");
        HttpServletRequest rr = (HttpServletRequest)servletRequest;
        //System.out.println(rr.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy.");
    }
}
