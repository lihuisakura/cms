package com.lihui.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.lihui.cms.domain.Link;
import com.lihui.cms.domain.Settings;
import com.lihui.cms.domain.Slide;
import com.lihui.cms.domain.User;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;
import com.lihui.cms.service.LinkService;
import com.lihui.cms.service.SettingsService;
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
	@Autowired
	private SettingsService settingsService;
	@Autowired
	private LinkService linkService;
	
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
		//最新文章
		PageInfo<Article> newArticle=articleService.getArticleList(article, pageNum, 3);
		m.addAttribute("newArticle", newArticle.getList());
		//判断是否有选择栏目  非空查询栏目下文章   空默认为热门
		if(article.getChannel_id()!=null) {
			//获取栏目下的所有分类
			List<Category> cates=channelService.categoryList(article.getChannel_id().toString());
			m.addAttribute("cates", cates);
			//查询文章
			PageInfo<Article> pageInfo=articleService.getArticleList(article,pageNum,4);
			m.addAttribute("articles", pageInfo);
		}else {
			//查询广告表，制作轮播图
			List<Slide> slides=slideService.slideList();
			m.addAttribute("slides", slides);
			//查询最热文章
			article.setHot(1);
			PageInfo<Article> pageInfo=articleService.getArticleList(article, pageNum, 3);
			m.addAttribute("hots", pageInfo);
		}
		//查询热门推荐
		article.setHot(1);
		PageInfo<Article> pageInfo1=articleService.getArticleList(article, 1, 3);
		m.addAttribute("hotsTen", pageInfo1);
		//
		m.addAttribute("article", article);
		
		//查询友情链接,显示最新的10条链接
		Link link=new Link();
		PageInfo<Link> links=linkService.selects(link, 1, 10);
		m.addAttribute("links", links);
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
	public Object loginAdmin(Settings settings,HttpSession session) {

		String password=settings.getAdmin_password();
		settings=settingsService.loginAdmin(settings);
		//把输入的密码  加密  过后 和数据库中的已有的加密的密码比对
		String md5Hex = DigestUtils.md5Hex(password);
		if(settings==null) {
			return "usernameNo";
		}else if(!settings.getAdmin_password().equals(md5Hex)) {
			return "passwordNot";
		}
		session.setAttribute("adminUser", settings);
		return "true";
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
	public Object registerAdmin(Settings settings) {
		Settings Testsettings=new Settings();
		Testsettings.setAdmin_username(settings.getAdmin_username());
		Testsettings=settingsService.loginAdmin(Testsettings);
		if(null!=Testsettings) {
			return "existUser";
		}
		//注册之前对密码进行md5加密  使用apach工具类进行加密
		String md5Hex = DigestUtils.md5Hex(settings.getAdmin_password());
		settings.setAdmin_password(md5Hex);
		settingsService.registerAdmin(settings);
		return "true";
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
