package com.atos.etalonTest.Filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        chain.doFilter(request, response);  // Continue processing the request

        long duration = System.currentTimeMillis() - startTime;

        logger.info("{} {} {} {} \"{}\" {} ms",
                req.getMethod(),
                req.getRequestURI(),
                req.getRemoteHost(),
                res.getStatus(),
                req.getHeader("User-Agent"),
                duration
        );
    }
}
