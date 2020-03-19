package com.lihui.cms.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.lihui.cms.domain.enums.ContentType;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;
import com.lihui.cms.service.LinkService;
import com.lihui.cms.service.SettingsService;
import com.lihui.cms.service.SlideService;
import com.lihui.cms.service.UserService;
import com.lihui.cms.thread.ChannelThread;
import com.lihui.cms.thread.HotThread;
import com.lihui.cms.thread.NewArticleThread;
import com.lihui.cms.thread.NewImageThread;
import com.lihui.cms.thread.SlideThread;

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
	@Autowired
	private RedisTemplate redisTemplate;

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
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public Object index(Model m, Article article, @RequestParam(defaultValue = "6") Integer pageSize,
			@RequestParam(defaultValue = "1") Integer pageNum) {
		long startTime = System.currentTimeMillis();
		// 运用redis查询所有栏目
		List<Channel> list = channelService.channelList();
		m.addAttribute("channelList", list);
		List range = redisTemplate.opsForList().range("channelList", 0, -1);
		if (range == null || range.size() == 0) {
			List<Channel> channelList = channelService.channelList();
			redisTemplate.opsForList().rightPushAll("channelList", channelList.toArray());
			m.addAttribute("channelList", channelList);
		} else {
			m.addAttribute("channelList", range);
		}
		// 查询文章类型为文字
		article.setContent_type(ContentType.HTML);
		// 一条线程获取栏目和分类，存入作用域中
		/*
		 * ChannelThread channelThread = new ChannelThread(m, channelService);
		 * ExecutorService channelPool = Executors.newCachedThreadPool();
		 * channelPool.execute(channelThread);
		 */
		// 用redis查询最新文章
		List newArticleRedis = redisTemplate.opsForList().range("newArticle", 0, -1);
		if (newArticleRedis == null || newArticleRedis.size() == 0) {
			PageInfo<Article> newArticle = articleService.getArticleList(article, pageNum, 3);
			redisTemplate.opsForList().rightPushAll("newArticle", newArticle.getList().toArray());
			redisTemplate.expire("newArticle", 5, TimeUnit.MINUTES);
			m.addAttribute("newArticle", newArticle.getList());
		} else {
			m.addAttribute("newArticle", newArticleRedis);
		}
		// 一条线程获取右侧最新文章，存入作用域中
		// NewArticleThread newArticleThread = new NewArticleThread(article, pageNum,
		// pageSize, articleService, m);
		// ExecutorService newArticlePool = Executors.newCachedThreadPool();
		// newArticlePool.execute(newArticleThread);

		// 判断是否有选择栏目 非空查询栏目下文章 空默认为热门
		if (article.getChannel_id() != null) {
			// 获取栏目下的所有分类
			List<Category> cates = channelService.categoryList(article.getChannel_id().toString());
			m.addAttribute("cates", cates);
			// 查询文章
			if(article.getChannel_id()==9) {
				article.setContent_type(ContentType.IMAGE);
			}
			PageInfo<Article> pageInfo = articleService.getArticleList(article, pageNum, 4);
			m.addAttribute("articles", pageInfo);
			article.setContent_type(ContentType.HTML);
		} else {
			// 查询广告表，制作轮播图,使用redis
			List slideList = redisTemplate.opsForList().range("slides", 0, -1);
			if (slideList == null || slideList.size() == 0) {
				List<Slide> slides = slideService.slideList();
				redisTemplate.opsForList().rightPushAll("slides", slides.toArray());
				m.addAttribute("slides", slides);
			} else {
				m.addAttribute("slides", slideList);
			}
			// 一条线程获取顶部幻灯片数据，存入作用域中
			/*
			 * SlideThread slideThread = new SlideThread(m, slideService); ExecutorService
			 * slidePool = Executors.newCachedThreadPool(); slidePool.execute(slideThread);
			 */
			// 查询最热文章
			article.setHot(1);

//			List hotList = redisTemplate.opsForList().range("hots", 0, -1);
//			if (hotList == null || hotList.size() == 0) {
//				PageInfo<Article> pageInfo = articleService.getArticleList(article, pageNum, 3);
//				redisTemplate.opsForList().rightPushAll("hots", pageInfo.getList().toArray());
//				redisTemplate.expire("hots", 5, TimeUnit.MINUTES);
//				m.addAttribute("hots", pageInfo.getList());
//			} else {
//				m.addAttribute("hots", hotList);
//			}

			PageInfo<Article> pageInfo = articleService.getArticleList(article, pageNum, 5);
			m.addAttribute("hots", pageInfo);
			// 一条线程获取首页热门文章，存入作用域中
			/*
			 * HotThread hotThread=new HotThread(article, pageNum, pageSize, articleService,
			 * m); ExecutorService service = Executors.newCachedThreadPool();
			 * service.execute(hotThread);
			 */
		}
		// 查询热门推荐,用redis
		article.setHot(1);
		List hotsTenList = redisTemplate.opsForList().range("hotsTen", 0, -1);
		if (hotsTenList == null || hotsTenList.size() == 0) {
			PageInfo<Article> pageInfo1 = articleService.getArticleList(article, 1, 3);
			redisTemplate.opsForList().rightPushAll("hotsTen", pageInfo1.getList().toArray());
			redisTemplate.expire("hotsTen", 5, TimeUnit.MINUTES);
			m.addAttribute("hotsTen", pageInfo1.getList());
		} else {
			m.addAttribute("hotsTen", hotsTenList);
		}

		m.addAttribute("article", article);
		// 查询友情链接,显示最新的10条链接
		Link link = new Link();
		List linkList = redisTemplate.opsForList().range("links", 0, -1);
		if (linkList == null || linkList.size() == 0) {
			PageInfo<Link> links = linkService.selects(link, 1, 10);
			redisTemplate.opsForList().rightPushAll("links", links.getList().toArray());
			m.addAttribute("links", links.getList());
		} else {
			m.addAttribute("links", linkList);
		}

		// 查询最新图片,使用redis
		article.setHot(0);
		article.setContent_type(ContentType.IMAGE);
		List newImageList = redisTemplate.opsForList().range("newImage", 0, -1);
		if (newImageList == null || newImageList.size() == 0) {
			PageInfo<Article> newImage = articleService.getArticleList(article, 1, 3);
			m.addAttribute("newImage", newImage.getList());
			redisTemplate.opsForList().rightPushAll("newImage", newImage.getList().toArray());
			redisTemplate.expire("newImage", 5, TimeUnit.MINUTES);
		} else {
			m.addAttribute("newImage", newImageList);
		}

		// 一条线程获取右侧“最新图片”，存入作用域中
		/*
		 * NewImageThread newImageThread = new NewImageThread(article, pageNum,
		 * pageSize, articleService, m); ExecutorService newImagePool =
		 * Executors.newCachedThreadPool(); newImagePool.execute(newImageThread);
		 */
		long endTime = System.currentTimeMillis();
		long time=endTime-startTime;
		System.err.println("首页访问需要："+time+"毫秒");
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
	public Object loginAdmin(Settings settings, HttpSession session) {

		String password = settings.getAdmin_password();
		settings = settingsService.loginAdmin(settings);
		// 把输入的密码 加密 过后 和数据库中的已有的加密的密码比对
		String md5Hex = DigestUtils.md5Hex(password);
		if (settings == null) {
			return "usernameNo";
		} else if (!settings.getAdmin_password().equals(md5Hex)) {
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
		Settings Testsettings = new Settings();
		Testsettings.setAdmin_username(settings.getAdmin_username());
		Testsettings = settingsService.loginAdmin(Testsettings);
		if (null != Testsettings) {
			return "existUser";
		}
		// 注册之前对密码进行md5加密 使用apach工具类进行加密
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
