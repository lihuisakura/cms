package com.lihui.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.lihui.cms.domain.User;

public interface UserDao {

	List<User> getUserList(@Param("username")String username);

	@Update("update cms_user set locked=#{locted} where id=#{id}")
	int updateLocated(@Param("id")String id, @Param("locted")String locted);

	User loginUser(@Param("username")String username,@Param("password")String password);

	@Insert("insert into cms_user values (null,#{username},#{password},null,null,null,null,null,null,null);")
	int registerUser(@Param("username")String username,@Param("password")String password);

	int loginAdmin(@Param("username")String username,@Param("password")String password);

	@Insert("insert into cms_admin values (null,#{username},#{password},null,null,null,null,null,null,null);")
	int registerAdmin(@Param("username")String username,@Param("password")String password);

}
