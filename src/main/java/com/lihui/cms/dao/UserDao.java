package com.lihui.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.lihui.cms.domain.User;

public interface UserDao {

	List<User> getUserList(@Param("username")String username);

	@Update("update cms_user set locked=#{locted} where id=#{id}")
	int updateLocated(@Param("id")String id, @Param("locted")String locted);

}
