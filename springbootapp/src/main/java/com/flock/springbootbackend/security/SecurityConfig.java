package com.flock.springbootbackend.security;

import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepo userRepo;
    @Autowired private JWTFilter filter;
    @Autowired private MyUserDetailsService uds;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers(Constants.UrlConstants.AUTH_URL).permitAll()
                .antMatchers(Constants.UrlConstants.CONTACTS_URL).hasRole(Constants.UserConstants.USER)
                .antMatchers(Constants.UrlConstants.USER_URL).hasRole(Constants.UserConstants.USER)
                .antMatchers(Constants.UrlConstants.CSV_URL).hasRole(Constants.UserConstants.USER)
                .and()
                .userDetailsService(uds)
                .exceptionHandling()
                .authenticationEntryPoint(

                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.AuthContants.UNAUTHORIZED)
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
