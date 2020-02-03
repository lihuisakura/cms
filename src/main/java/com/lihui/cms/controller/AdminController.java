package com.lihui.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lihui.cms.domain.Channel;
import com.lihui.cms.service.ChannelService;

@Controller
public class AdminController {

	@Autowired
	private ChannelService channelService;
	
	/**
	 * 
	 * @Title: 进入后台管理
	 * @Description: TODO
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/admin")
	public Object admin() {
		return "admin/index";
	}
	/**
	 * 
	 * @Title: 进入个人中心
	 * @Description: TODO
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/my")
	public Object my() {
		return "my/index";
	}
	/**
	 * 
	 * 
	 * @Title: 进入今日头条首页
	 * @Description: TODO
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/index")
	public Object index(Model m) {
		
		  List<Channel> list=channelService.channelList();
		  m.addAttribute("channelList", list);
		 
		return "index/index";
	}
}
