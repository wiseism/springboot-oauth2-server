package com.china.book.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private TokenStore tokenStore;
    @RequestMapping("/login")
    public String login() {
        return "oauth/login";
    }
    @RequestMapping("/")
    public String index() {
        return "layout/index";
    }
    @GetMapping("/logout")
    @ResponseBody
    public String logout(@RequestHeader String Authorization) {
        if (!Authorization.isEmpty()){
            String token=Authorization.split(" ")[1];
            OAuth2AccessToken auth2AccessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(auth2AccessToken);
            return "SUCCESS";
        }else{
            return "FAIL";
        }
       
    }
}