package net.javaguides.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
         	.cors()
         	.and()
         	.csrf().disable()
            .authorizeRequests()
                // Public endpoints
                .antMatchers(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                ).permitAll()

                // Admin-only endpoints
                .antMatchers("/api/admin/**").hasRole("ADMIN")

                // User-only endpoints (optional)
                .antMatchers("/api/user/**").hasRole("USER")

                // All authenticated users (admin or user)
                .antMatchers("/api/test/**").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}