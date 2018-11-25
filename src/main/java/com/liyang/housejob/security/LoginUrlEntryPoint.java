package com.liyang.housejob.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private PathMatcher matcher = new AntPathMatcher();
    private final Map<String,String> authEntryPointMap;

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        this.authEntryPointMap=new HashMap<>();

        authEntryPointMap.put("/user/**","/user/login");
        authEntryPointMap.put("/admin/**","/admin/login");
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String uri = request.getRequestURI().replace(request.getContextPath(),"");
        for (Map.Entry<String,String> authEntry :authEntryPointMap.entrySet()){
            if (this.matcher.match(authEntry.getKey(),uri)){
                return authEntry.getValue();
            }
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
