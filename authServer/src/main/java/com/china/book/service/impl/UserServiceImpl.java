package com.china.book.service.impl;

import com.china.book.dao.UserEntityDao;
import com.china.book.domain.UserEntity;
import com.china.book.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserEntityDao userDao;

    @Override
    public UserEntity findByname(String username) {
        return userDao.findByUsername(username) ;
    }
}
