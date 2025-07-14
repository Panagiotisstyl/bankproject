package com.root.bankproject.authentication;

import com.root.bankproject.encryption.TokenFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SimpleAuthFilter extends OncePerRequestFilter {


    private final TokenFactory tokenFactory;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String url = request.getRequestURI();
        String authorizationHeader = request.getHeader("Authorization");


        if (url.startsWith("/api/v1/accounts/addUser")){
            String[] parts = url.split("/");
            int accountId = Integer.parseInt(parts[5]);
            if (authorizationHeader == null || !tokenFactory.validateToken(authorizationHeader,accountId)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized");
                return;
            }
        }
        if(url.startsWith("/api/v1/accounts/deposit")){

            if (authorizationHeader == null || !tokenFactory.validateTokenForDeposit(authorizationHeader)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized");
                return;
            }
        }

        if(url.startsWith("/api/v1/accounts/withdraw")){
            String[] parts = url.split("/");
            int accountId = Integer.parseInt(parts[5]);
            if (authorizationHeader == null || !tokenFactory.validateToken(authorizationHeader,accountId)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
