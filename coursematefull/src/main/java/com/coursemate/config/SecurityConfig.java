package com.coursemate.config;
import com.coursemate.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    CustomUserDetailsService customUserDetailsService;
    @Autowired
public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
    System.out.println("CustomUserDetailsService injected: " + customUserDetailsService);
    this.customUserDetailsService = customUserDetailsService;
}
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();  // Password encoder for hashing passwords
    // }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOpPasswordEncoder for plain text passwords (only for development/testing)
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                   .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     // Create a default user with username "user" and password "password"
    //     UserDetails user = User.withUsername("user")
    //                            .password(passwordEncoder().encode("password"))
    //                            .roles("USER")
    //                            .build();

    //     return new InMemoryUserDetailsManager(user);
    // }

    // // Configure HTTP security for Spring Security 6.x+
    // public void configure(HttpSecurity http) throws Exception {
    //     http
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/signup/student", "/","/signup/teacher", "/signup/administrator", "/login","/dashboard/**","/dashboard/student/**","/courses/**")
                .permitAll() // Allow access to signup and login pages
                .anyRequest().authenticated() // All other requests need authentication
            )
            // .formLogin(form -> form
            //     .loginPage("/login") // Specify the custom login page URL
            //     .permitAll() // Allow access to the login page for everyone
            //     //.defaultSuccessUrl("/dashboard/**", true)  // Redirect to dashboard after successful login
            // )
            .logout(logout -> logout
                .permitAll() // Allow logout for everyone
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .csrf().disable(); // Disable CSRF for simplicity (optional, for development purposes)

        return http.build();
    }
    // Configure HTTP security for Spring Security 6.x+
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authz -> authz
    //             .requestMatchers("/signup/student", "/signup/teacher", "/signup/administrator", "/login").permitAll()  // Allow signup and login pages for everyone
    //             .anyRequest().authenticated()         // All other requests need authentication
    //         )
    //         .formLogin(form -> form
    //             .loginPage("/login")  // Specify the login page URL
    //             .permitAll()          // Allow access to login page for everyone
    //         )
    //         .logout(logout -> logout
    //             .permitAll()  // Allow logout for everyone
    //         );
        
    // }
}
