package com.china.book.service;

import com.china.book.domain.UserEntity;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public interface IUserService {
    
    UserEntity findByname(String username);
}
