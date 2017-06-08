package org.smartframework.platform.context;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmartFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        SmartContext.setRequest(request);
        SmartContext.setResponse(response);

        try {
            chain.doFilter(req, res);
        } finally {
            SmartContext.clear();
        }
    }

    public void destroy() {
    }
}
