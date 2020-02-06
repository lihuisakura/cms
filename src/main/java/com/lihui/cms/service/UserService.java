package com.lihui.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Settings;
import com.lihui.cms.domain.User;

public interface UserService {

	PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize);

	int updateLocated(String id, String locted);

	User loginUser(User user);

	Boolean registerUser(User user);

	

}
