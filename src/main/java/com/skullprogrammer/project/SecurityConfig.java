package com.skullprogrammer.project;

import com.skullprogrammer.project.error.handler.AuthenticationFailureHandlerImpl;
import com.skullprogrammer.project.filters.CustomAuthenticationFilter;
import com.skullprogrammer.project.filters.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity(debug = false )
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    //constructor based injection
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //@Autowired
    private final AuthenticationEntryPoint authEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;


   @Lazy
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                          @Qualifier("authenticationEntryPointImpl") AuthenticationEntryPoint authEntryPoint,
                          @Qualifier("accessDeniedHandlerImpl") AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Filters Section
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //Exception Handling Section
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

//        http.oauth2ResourceServer().authenticationEntryPoint(authEntryPoint);
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //Configuration Section
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/**").hasAuthority(ROLE.USER);
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    AuthenticationFailureHandlerImpl authenticationFailureHandler(){
       return new AuthenticationFailureHandlerImpl();
    }

    @Bean
    CustomAuthorizationFilter customAuthorizationFilter(){
       return new CustomAuthorizationFilter();
    }

//    @Bean
//    AccessDeniedHandlerImpl accessDeniedHandler(){
//        return new AccessDeniedHandlerImpl();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
