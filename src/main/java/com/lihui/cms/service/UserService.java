package com.lihui.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.User;

public interface UserService {

	PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize);

	int updateLocated(String id, String locted);

	User loginUser(String username,String password);

	Boolean registerUser(String username, String password);

	Boolean loginAdmin(String username, String password);

	Boolean registerAdmin(String username, String password);

}
