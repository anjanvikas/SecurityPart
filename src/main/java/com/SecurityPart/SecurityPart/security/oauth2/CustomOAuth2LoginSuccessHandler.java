package com.SecurityPart.SecurityPart.security.oauth2;

import java.io.IOException;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SecurityPart.SecurityPart.security.jwt.JwtUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private JwtUtils jwtUtils;

    public CustomOAuth2LoginSuccessHandler(JwtUtils jwtUtils){
        this.jwtUtils=jwtUtils;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                // generate jwt and send the token to user
                OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

                String jwtToken = this.jwtUtils.generateJwtCookie(oAuth2User).toString();

                
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("Set-Cookie", jwtToken);
                response.getWriter().write("{\"message\": \"Authentication successful!\"}");
    }

}
