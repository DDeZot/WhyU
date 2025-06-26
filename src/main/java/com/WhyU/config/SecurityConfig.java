package com.WhyU.config;

import com.WhyU.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("welcome").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/users/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "images/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "api/users/{id}/username").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/telegram_users").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/telegram_users/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/stories").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/stories/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/stories/{id}/start").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/actions/{id}/consequence").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/frames/{id}/actions").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/frames/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/telegram_users/{id}/user").authenticated()
                        .requestMatchers("api/results/**").authenticated()
                        .requestMatchers("api/stories/**",
                                "api/frames/**",
                                "api/actions/**",
                                "api/attachments/**",
                                "api/results/**").hasRole("ADMIN"))
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();

    }

    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(UserServiceImpl userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
}
