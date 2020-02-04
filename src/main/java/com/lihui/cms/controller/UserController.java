package com.lihui.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		user=userService.loginUser(user.getUsername(),user.getPassword());
		
		if(user!=null) {
			session.setAttribute("user", user);
			return true;
		}
		return false;
	}
	
	
	@ResponseBody
	@RequestMapping("/registerUser")
	public Object registerUser(User user) {
		Boolean flag=userService.registerUser(user.getUsername(),user.getPassword());
		return flag;
	}
}
