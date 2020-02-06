package com.lihui.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.User;
import com.lihui.cms.service.UserService;
import com.lihui.cms.util.PageUtil;

@RequestMapping("user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @Title: selects 
	 * @Description: 查看用户列表
	 * @param m
	 * @param username
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selects")
	public Object selects(Model m, @RequestParam(defaultValue = "") String username,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize) {
		PageInfo<User> info = userService.getUserList(username, pageNum, pageSize);
		//String page = PageUtil.page(pageNum, info.getPages(), "/user/selects?username=" + username, pageSize);
		m.addAttribute("page", info);
		m.addAttribute("username", username);
		m.addAttribute("list", info.getList());
		return "admin/user";
	}
	/**
	 * 
	 * @Title: updateLocated 
	 * @Description: 修改用户状态
	 * @param id
	 * @param locted
	 * @return
	 * @return: Object
	 */
	@RequestMapping("updateLocated")
	@ResponseBody
	public Object updateLocated(String id,String locted) {
		int i=userService.updateLocated(id,locted);
		return i>=1?true:false;
	}
	
	/**
	 * 
	 * @Title: login 
	 * @Description: 进入用户登陆界面
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/login")
	public Object login() {
		return "public/login";
	}
	/**
	 * 
	 * @Title: logout
	 * @Description: 注销
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/logout")
	public Object logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/index";
	}
	/**
	 * 
	 * @Title: register 
	 * @Description: 进入用户注册界面
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/register")
	public Object register() {
		return "public/register";
	}

	/**
	 * 
	 * @Title: loginUser 
	 * @Description: 用户登录
	 * @param user
	 * @param session
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("/loginUser")
	public Object loginUser(User user,HttpSession session) {
		
		String password=user.getPassword();
		user=userService.loginUser(user);
		//把输入的密码  加密  过后 和数据库中的已有的加密的密码比对
		String md5Hex = DigestUtils.md5Hex(password);
		if(user==null) {
			return "usernameNo";
		}else if(user.getLocked()==1) {
			return "locked";
		}else if(!user.getPassword().equals(md5Hex)) {
			return "passwordNot";
		}
		session.setAttribute("user", user);
		return "true";
		
	}
	
	/**
	 * 
	 * @Title: registerUser 
	 * @Description: 注册用户
	 * @param user
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("/registerUser")
	public Object registerUser(User user) {
		User testUser =new User();
		testUser.setUsername(user.getUsername());
		testUser=userService.loginUser(testUser);
		if(null!=testUser) {
			return "existUser";
		}
		//注册之前对密码进行md5加密  使用apach工具类进行加密
		String md5Hex = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Hex);
		userService.registerUser(user);
		return "true";
	}
}
