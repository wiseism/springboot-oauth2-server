package com.china.book.api;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiangyun on 2018/8/30.
 */
@RestController
@Slf4j
public class BookController {
    @GetMapping("/book/classical")
    public JSONObject getBook() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName()+":访问了书店");
        JSONObject bookJson = new JSONObject();
        bookJson.put("1", "西游记");
        bookJson.put("2", "水浒传");
        bookJson.put("3", "红楼梦");
        bookJson.put("4", "三国演义");
        return bookJson;
    }
    @PostMapping("/book/sell")
    public JSONObject sellBook() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject bookJson = new JSONObject();
        bookJson.put("4", "三国演义");
        log.info(authentication.getName()+":买了一本书"+bookJson.toJSONString());
        return bookJson;
    }
}
