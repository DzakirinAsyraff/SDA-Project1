package sda.project.SRAD.Config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sda.project.SRAD.Services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    private AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Please note wildcards are not really good idea, as it means this interceptor
        // will be run for every request, even for static resources like css or js
        registry
            .addInterceptor(new EndpointInterceptor(authService))
            .addPathPatterns("/**");
    }

}