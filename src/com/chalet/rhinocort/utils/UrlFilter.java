package com.chalet.rhinocort.utils;
/**
 * @author Chalet
 * @version 创建时间：2013年12月8日 上午1:29:32
 * 类说明
 */
import java.io.IOException;
import java.net.URLDecoder;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
public class UrlFilter implements Filter {
 
    public final static String DEFAULT_URI_ENCODE = "UTF-8";
     
    private FilterConfig config = null;
    private String encode = null;
     
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.encode = config.getInitParameter("DEFAULT_URI_ENCODE");
        if(this.encode == null) {
            this.encode = DEFAULT_URI_ENCODE;
        }
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        String ch = URLDecoder.decode(uri, encode);
        if(uri.equals(ch)) {
            chain.doFilter(req, res);
            return;
        }
        ch = ch.substring(request.getContextPath().length());
        config.getServletContext().getRequestDispatcher(ch).forward(req, res);
    }    
 
    @Override
    public void destroy() {
        config = null;
    }
}
