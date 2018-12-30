package com.github.alexanderfefelov.bgbilling.servlet.demo;

import org.apache.log4j.Logger;
import ru.bitel.common.logging.NestedContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("init");
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    public void destroy() {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("destroy");
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doGet");
            PrintWriter writer = response.getWriter();
            writer.println("Hello, World!");
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doPost");
            super.doPost(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doPut");
            super.doPut(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doDelete");
            super.doDelete(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doHead");
            super.doHead(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    private Logger logger = Logger.getLogger(this.getClass());

    private static final String LOG_CONTEXT = "servlet";

}
