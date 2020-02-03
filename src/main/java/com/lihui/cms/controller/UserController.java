package com.lihui.cms.controller;

import java.util.List;

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
	 * @Description: TODO
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

}
