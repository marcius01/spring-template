package com.skullprogrammer.project.error.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("accessDeniedHandlerImpl")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        resolver.resolveException(request, response, null, accessDeniedException);
    }

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException,
//            ServletException {
//
//        ErrorType errorResponse = ErrorType.USER_NOT_AUTHORIZED;
//        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
//        response.setStatus(errorResponse.getStatus().value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        OutputStream out = response.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writerWithDefaultPrettyPrinter().writeValue(out, result);
//        out.flush();
//    }
}
