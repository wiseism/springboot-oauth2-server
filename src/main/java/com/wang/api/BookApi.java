package com.wang.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookApi {
    @GetMapping("/book/sellBook")
    public String sellBook() {
        return "四大名著";
    }
}
