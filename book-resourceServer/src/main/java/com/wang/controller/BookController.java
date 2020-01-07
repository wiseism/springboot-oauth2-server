package com.wang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {

    @RequestMapping("/list")
    public String listBooks() {
        return "ok";
    }


}