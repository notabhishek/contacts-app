package com.flock.springbootbackend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flock.springbootbackend.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired private MyUserDetailsService userDetailsService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(Utils.AuthContants.AUTHORIZATION);

        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith(Utils.AuthContants.BEARER_)){
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, Utils.AuthContants.INVALID_JWT_TOKEN_IN_BEARER_HEADER);
            }else {
                try{
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch(JWTVerificationException exc){

                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, Utils.AuthContants.INVALID_JWT_TOKEN);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
