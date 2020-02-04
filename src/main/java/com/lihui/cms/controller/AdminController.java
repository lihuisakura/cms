package com.lihui.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.domain.Slide;
import com.lihui.cms.domain.User;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;
import com.lihui.cms.service.SlideService;
import com.lihui.cms.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private SlideService slideService;
	
	/**
	 * 
	 * @Title: admin
	 * @Description: 进入后台管理
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/admin")
	public Object admin() {
		return "admin/index";
	}
	/**
	 * 
	 * @Title: my
	 * @Description: 进入个人中心
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
	 * @Title: index
	 * @Description: 进入今日头条首页
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/index")
	public Object index(Model m,Article article,@RequestParam(defaultValue = "6")Integer pageSize,@RequestParam(defaultValue = "1")Integer pageNum) {
		//查询所有栏目
		List<Channel> list=channelService.channelList();
		m.addAttribute("channelList", list);
		//判断是否有选择栏目  非空查询栏目下文章   空默认为热门
		if(article.getChannel_id()!=null) {
			//获取栏目下的所有分类
			List<Category> cates=channelService.categoryList(article.getChannel_id().toString());
			m.addAttribute("cates", cates);
			//查询文章
			PageInfo<Article> pageInfo=articleService.getArticleList(article,pageNum,pageSize);
			m.addAttribute("articles", pageInfo.getList());
			
		}else {
			//查询广告表，制作轮播图
			List<Slide> slides=slideService.slideList();
			m.addAttribute("slides", slides);
			//查询最热文章
			article.setHot(1);
			PageInfo<Article> pageInfo=articleService.getArticleList(article, pageNum, pageSize);
			m.addAttribute("hots", pageInfo.getList());
		}
		m.addAttribute("article", article);
		return "index/index";
	}
	/**
	 * 
	 * @Title: login 
	 * @Description: 进入管理员登陆界面
	 * @param m
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/login")
	public Object login() {
		return "admin/login";
	}
	/**
	 * 
	 * @Title: register 
	 * @Description: 进入管理员注册界面
	 * @return
	 * @return: Object
	 */
	@RequestMapping("/register")
	public Object register() {
		return "admin/register";
	}
	/**
	 * 
	 * @Title: loginAdmin
	 * @Description: 管理员登录
	 * @param user
	 * @param session
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("/loginAdmin")
	public Object loginAdmin(User user,HttpSession session) {
		Boolean flag=userService.loginAdmin(user.getUsername(),user.getPassword());
		if(flag) {
			session.setAttribute("adminUser", user);
		}
		return flag;
	}
	
	/**
	 * 
	 * @Title: registerAdmin 
	 * @Description: 管理员注册
	 * @param user
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("/registerAdmin")
	public Object registerAdmin(User user) {
		Boolean flag=userService.registerAdmin(user.getUsername(),user.getPassword());
		return flag;
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
		session.removeAttribute("adminUser");
		return "admin/index";
	}
	
}
