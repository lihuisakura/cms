package com.lihui.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.dao.UserDao;
import com.lihui.cms.domain.User;
import com.lihui.cms.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<User> userList = userDao.getUserList(username);
		return new PageInfo<User>(userList);
	}

	@Override
	public int updateLocated(String id, String locted) {
		// TODO Auto-generated method stub
		return userDao.updateLocated(id,locted);
	}

	@Override
	public User loginUser(String username,String password) {
		// TODO Auto-generated method stub
		return userDao.loginUser(username,password);
	}

	@Override
	public Boolean registerUser(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.registerUser(username,password)>0;
	}

	@Override
	public Boolean loginAdmin(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.loginAdmin(username,password)>0;
	}

	@Override
	public Boolean registerAdmin(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.registerAdmin(username,password)>0;
	}
}
