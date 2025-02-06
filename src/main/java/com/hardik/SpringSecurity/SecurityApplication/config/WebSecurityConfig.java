package com.hardik.SpringSecurity.SecurityApplication.config;

import com.hardik.SpringSecurity.SecurityApplication.entities.enums.Role;
import com.hardik.SpringSecurity.SecurityApplication.filter.JwtAuthFilter;
import com.hardik.SpringSecurity.SecurityApplication.handler.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.hardik.SpringSecurity.SecurityApplication.entities.enums.Permission.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes={
            "/error","/auth/**","/home.html"
    };

   @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(publicRoutes).permitAll()
//                        .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/post/**").hasAnyRole(Role.ADMIN.name(),Role.CREATOR.name())
//                        .requestMatchers(HttpMethod.POST,"/post/**").hasAuthority(POST_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT,"/post/**").hasAuthority(POST_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/post/**").hasAuthority(POST_DELETE.name())
//                        .requestMatchers("/post/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                        .csrf(csrfConfig->csrfConfig.disable())
                        .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config-> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler));
//                .formLogin(Customizer.withDefaults());

        return  httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails userDetails= User.withUsername("har")
//                .password(passwordEncoder().encode("haru"))
//                .roles("DungenMaster")
//                .build();
//        UserDetails userAdminDetails= User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(userAdminDetails,userDetails);
//
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
    }

}
