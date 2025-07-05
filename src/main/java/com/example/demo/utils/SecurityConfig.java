package com.example.demo.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register", "/api/login", "/doc/**").permitAll() // 允许匿名访问
                        .anyRequest().authenticated() // 其他接口需要认证
                )
                .httpBasic(AbstractHttpConfigurer::disable) // 禁用Basic认证
                .formLogin(AbstractHttpConfigurer::disable); // 禁用表单登录

        return http.build();
    }
}