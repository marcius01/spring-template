package com.skullprogrammer.project.error.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler
{
    @Autowired
    @Qualifier("authenticationEntryPointImpl")
    private AuthenticationEntryPoint authEntryPoint;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        this.authEntryPoint.commence(request, response, exception);
    }

//        @Override
//        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//            AuthenticationException exception) throws IOException, ServletException
//        {
//        ErrorType errorResponse = ErrorType.USER_NOT_AUTHORIZED;
//        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
//        httpServletResponse.setStatus(errorResponse.getStatus().value());
//        OutputStream out = httpServletResponse.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writerWithDefaultPrettyPrinter().writeValue(out, result);
//        out.flush();
//
//    }

}