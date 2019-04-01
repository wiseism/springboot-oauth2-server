package com.wang.service.impl;

import com.wang.dao.PermissionDao;
import com.wang.dao.UserDao;
import com.wang.domain.PermissionEntity;
import com.wang.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.access.UserCore;
import org.springframework.security.oauth2.provider.authentication.PermissionGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserDao userDao;
	@Autowired
	PermissionDao permissionDao;



	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userDao.findByUsername(username);
		if (user != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			UserCore userCore=new UserCore(user.getUsername(), user.getPassword(), grantedAuthorities);
			userCore.setUserId(user.getId()+"");
			return userCore;
		} else {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}
	}
}