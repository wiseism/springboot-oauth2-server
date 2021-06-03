package com.china.book.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wangxiangyun on 2018/9/10.
 */
public class LoginParameter {
    @NotNull(message = "用户ID不能为空")
    private String userId;
    @Size(message = "长度不够",min = 10,max = 900)
    private String name;
    private String mark;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMark() {
        return mark;
    }
    
    public void setMark(String mark) {
        this.mark = mark;
    }
    
    @Override
    public String toString() {
        return userId+mark+name;
    }
}
