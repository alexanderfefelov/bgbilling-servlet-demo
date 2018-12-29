package com.github.alexanderfefelov.bgbilling.servlet.demo;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        logger.trace("init");
    }

    @Override
    public void destroy() {
        logger.trace("destroy");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("doGet");
        PrintWriter writer = response.getWriter();
        writer.println("Hello, World!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("doPost");
        super.doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("doPut");
        super.doPut(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("doDelete");
        super.doDelete(request, response);
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.trace("doHead");
        super.doHead(request, response);
    }

    private Logger logger = Logger.getLogger(this.getClass());

}
