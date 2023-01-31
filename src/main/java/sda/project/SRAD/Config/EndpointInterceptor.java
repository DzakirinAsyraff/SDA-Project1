package sda.project.SRAD.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sda.project.SRAD.Services.AuthService;


// An interceptor that is run before every endpoint request
public class EndpointInterceptor implements HandlerInterceptor {

    private AuthService authService;

    @Autowired
    public EndpointInterceptor(AuthService authService) {
        this.authService = authService;
    }



    @Override
    public boolean preHandle(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Object handler
    ) throws Exception {
        request.setAttribute("user", authService.getLoggedInUser());
        return true;
    }
} 

