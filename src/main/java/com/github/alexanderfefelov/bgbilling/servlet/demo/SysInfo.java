package com.github.alexanderfefelov.bgbilling.servlet.demo;

import bitel.billing.common.VersionInfo;
import org.apache.log4j.Logger;
import ru.bitel.bgbilling.common.BGException;
import ru.bitel.bgbilling.kernel.module.common.bean.BGModule;
import ru.bitel.bgbilling.kernel.module.server.ModuleCache;
import ru.bitel.common.logging.NestedContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class SysInfo extends HttpServlet {

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
            writer.println(collectSysInfo());
        } catch (BGException bge) {
            // Something went wrong
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

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doOptions");
            super.doOptions(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    @Override
    protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NestedContext.push(LOG_CONTEXT);
            logger.trace("doTrace");
            super.doTrace(request, response);
        } finally {
            NestedContext.pop();
        }
    }

    private String collectSysInfo() throws BGException, UnknownHostException {
        return String.join(NL,
            collectModules(),
            collectRuntime()
        );
    }

    private String collectModules() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.join(NL,
            "Modules",
            HR,
            NL
        ));
        VersionInfo kernelVer = VersionInfo.getVersionInfo("server");
        buffer.append(String.join(SPACE,
            "0",
            kernelVer.getModuleName(),
            kernelVer.getVersionString()
        ));
        buffer.append(NL);
        List<BGModule> modules = ModuleCache.getInstance().getModulesList();
        for (BGModule module : modules) {
            VersionInfo ver = VersionInfo.getVersionInfo(module.getName());
            buffer.append(String.join(SPACE,
                String.valueOf(module.getId()),
                ver.getModuleName(),
                ver.getVersionString()
            ));
            buffer.append(NL);
        }
        return buffer.toString();
    }

    private String collectRuntime() throws UnknownHostException {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.join(NL,
            "Runtime",
            HR,
            NL
        ));
        InetAddress ipAddress = InetAddress.getLocalHost();
        buffer.append(String.join(NL,
            "Hostname/IP address: " + ipAddress,
            "Available processors: " + Runtime.getRuntime().availableProcessors(),
            "Memory free / max / total, MB: "
                + Runtime.getRuntime().freeMemory() / MB + " / "
                + Runtime.getRuntime().maxMemory() / MB + " / "
                + Runtime.getRuntime().totalMemory() / MB
        ));
        return buffer.toString();
    }

    private Logger logger = Logger.getLogger(this.getClass());

    private static final String LOG_CONTEXT = "servlet";

    private final static String HR = "--------------------------------------------------";
    private final static String NL = "\n";
    private final static String SPACE = " ";
    private final static int MB = 1024 * 1024;

}
