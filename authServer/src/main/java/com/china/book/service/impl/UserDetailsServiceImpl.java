package com.china.book.service.impl;


import com.china.book.domain.UserCore;
import com.china.book.domain.UserEntity;
import com.china.book.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IUserService iUserService;

	@Override
	public UserDetails loadUserByUsername(String username)  {
		UserEntity userEntity= iUserService.findByname(username);
		if (userEntity != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			User userCore=new User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
//			userCore.setUserId(userEntity.getId()+"");
			return userCore;
		} else {
			throw new UsernameNotFoundException("用户" + username + "不存在");
		}
	}

}