package com.ticketpro.movie_service.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class MdcLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String traceId = request.getHeader("X-Trace-Id");
            MDC.put("traceId", traceId);
            MDC.put("method", request.getMethod());
            MDC.put("path", request.getRequestURI());


            filterChain.doFilter(request, response);
        } finally {
            MDC.clear(); // Very important to prevent leakage in thread pools
        }
    }
}
