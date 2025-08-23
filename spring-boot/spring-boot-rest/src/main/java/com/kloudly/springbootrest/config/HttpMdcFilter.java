package com.kloudly.springbootrest.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class HttpMdcFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, java.io.IOException {
        long start = System.currentTimeMillis();
        try {
            MDC.put("method", req.getMethod());
            MDC.put("path", req.getRequestURI());
            MDC.put("status", Integer.toString(res.getStatus()));
            MDC.put("duration_ms", Long.toString(System.currentTimeMillis() - start));
            chain.doFilter(req, res);
        } finally {
            MDC.clear();
        }
    }
}
