//package com.example.thymeleaf3.config;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@WebFilter(value = "/time")
//public abstract class TimezoneValidateFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//
//        String timeParamm = httpRequest.getParameter("timezone");
//        if (timeParamm == null) {
//            timeParamm = "UTC";
//        } else {
//            timeParamm = timeParamm.replace("UTC ", "").trim();
//            timeParamm = timeParamm.replace("UTC+", "").trim();
//            timeParamm = timeParamm.replace("UTC-", "").trim();
//
//            if (!isValidTimezone(timeParamm)) {
//                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone " + timeParamm);
//                return;
//            }
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    private boolean isValidTimezone(String timezone) {
//        try {
//            if (!timezone.matches("\\d+") || Integer.parseInt(timezone) > 23 || Integer.parseInt(timezone) < -23) {
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}