package com.example.thymeleaf3.web;

import com.example.thymeleaf3.config.TemplateEngineUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet(value = "/time")
public class IndexServlet extends HttpServlet {

//    static class Employee {
//        String firstName;
//
//        public Employee(String name) {
//            this.firstName = name;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String timeParamm = httpServletRequest.getParameter("timezone");
        if (timeParamm == null){
            timeParamm = "UTC";
        }

        String timeParam = timeParamm.replace("UTC ", "UTC+");
        timeParam = timeParam.replace("UTC", "GMT");
        TimeZone timeZone;

        timeZone = TimeZone.getTimeZone(timeParam);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);
        String currentTime = dateFormat.format(currentDate);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(httpServletRequest.getServletContext());

        WebContext context = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext());

        context.setVariable("currentTime1", currentTime);

        engine.process("index.html", context, httpServletResponse.getWriter());
    }

}
