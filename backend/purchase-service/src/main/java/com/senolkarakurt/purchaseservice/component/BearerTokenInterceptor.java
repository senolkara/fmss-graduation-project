package com.senolkarakurt.purchaseservice.component;

import com.senolkarakurt.dto.BearerTokenWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BearerTokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final BearerTokenWrapper bearerTokenWrapper;

    public BearerTokenInterceptor(BearerTokenWrapper bearerTokenWrapper) {
        this.bearerTokenWrapper = bearerTokenWrapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(BearerTokenWrapper.token);
        final String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")) {
            String token = authorizationHeaderValue.substring(7);
            BearerTokenWrapper.setToken(token);
            System.out.println(BearerTokenWrapper.token);
        }
        request.setAttribute(AUTHORIZATION_HEADER, BearerTokenWrapper.token);
        return true;
    }
}
