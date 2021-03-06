package com.github.alexanderfefelov.bgbilling.servlet.demo;

import org.apache.log4j.Logger;
import ru.bitel.common.logging.NestedContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TerryPratchettFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("init");
        } finally {
            NestedContext.pop();
        }
    }

    public void destroy() {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("destroy");
        } finally {
            NestedContext.pop();
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doFilter");

            chain.doFilter(request, response);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        } finally {
            NestedContext.pop();
        }
    }

    private Logger logger = Logger.getLogger(this.getClass());

    private static final String LOG_CONTEXT = "servlet";

}
