package com.example.thymeleaf3.config;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String timeParam = httpServletRequest.getParameter("timezone");

        String lastTimezone = getTimeFromCookie(httpServletRequest);

        String timezone;
        if (lastTimezone != null){
            timezone = lastTimezone;
        } else if (timeParam != null) {
            timezone = timeParam;
        }else {
            timezone = "UTC";
        }
        timezone = timezone.replace("%2b", "+");
        timezone = timezone.replace("32", "+");

        char charToReplace = 32;
        char replacementChar = '+';

        timezone = replacer(timezone, charToReplace, replacementChar);

        saveCookie(httpServletResponse, timezone);

        TimeZone timeZone = TimeZone.getTimeZone("GMT+" + timezone);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);
        String currentTime = dateFormat.format(currentDate);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(httpServletRequest.getServletContext());

        WebContext context = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext());
        context.setVariable("currentTime1", currentTime);

        engine.process("index.html", context, httpServletResponse.getWriter());
    }

    private String getTimeFromCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastTimezone".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private static String replacer(String input, char charToReplace, char replacementChar) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == charToReplace) {
                charArray[i] = replacementChar;
            }
        }
        return new String(charArray);
    }

    private void saveCookie(HttpServletResponse httpServletResponse, String timezone) {
        Cookie cookie = new Cookie("lastTimezone", timezone);
        httpServletResponse.addCookie(cookie);
    }
}
