package com.SecurityPart.SecurityPart.security.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    @Autowired
    private UserRepo userRepo;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("OAuth2 User loading...");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //extract the user details
        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        logger.info("Loaded user from Google: GoogleId={}, Email={}, Name={}", googleId, email, name);
        //save or update in the db
        User user = userRepo.findByGoogleId(googleId)
                            .orElseGet(() -> {
                                User newUser = new User();

                                newUser.setGoogleId(googleId);
                                newUser.setEmail(email);
                                newUser.setProfilePicture(picture);
                                newUser.setUserName(name);
                                logger.info("Saving new user: {}", newUser);
                                
                                return userRepo.save(newUser);
                            });

        logger.info("Returning OAuth2User: {}", oAuth2User);
        return oAuth2User;
    }

}
