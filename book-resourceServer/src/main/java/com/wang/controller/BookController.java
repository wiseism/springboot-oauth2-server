package com.wang.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {

    @RequestMapping("/list")
    public User listBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User)   authentication.getPrincipal();
        return user;
    }


}