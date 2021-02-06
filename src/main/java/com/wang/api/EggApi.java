package com.wang.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EggApi {
    @GetMapping("/egg/sellEgg")
    public String sellEgg() {
        return "一个鸡蛋";
    }
}
