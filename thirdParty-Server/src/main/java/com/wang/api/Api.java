package com.wang.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiangyun on 2019/4/8.
 */
@RestController
public class Api {
    @Autowired
    private OAuth2RestTemplate clientCredentialsRestTemplate;
    @Autowired
    private OAuth2RestTemplate codeRestTemplate;

    @RequestMapping("/list")
    private String bookList() {
        String result = codeRestTemplate.getForObject("http://localhost:8000/list", String.class);
        return result;
    }
}
