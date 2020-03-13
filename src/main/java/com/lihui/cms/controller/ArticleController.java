package com.lihui.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.dao.ArticleRepository;
import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.domain.Content;
import com.lihui.cms.domain.User;
import com.lihui.cms.domain.enums.ContentType;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;
import com.lihui.cms.util.HLUtils;


@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private KafkaTemplate<String, String> kafka;

	/**
	 * 
	 * @Title: selects 
	 * @Description: 文章管理，查询文章列表，实现分页
	 * @param m
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selects")
	public Object selects(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {

		PageInfo<Article> page = articleService.getstatusList(article, pageNum,
				pageSize);

		m.addAttribute("page", page);
		m.addAttribute("title", article.getTitle());
		m.addAttribute("status", article.getStatus());
		m.addAttribute("list", page.getList());

		return "admin/article";
	}

	/**
	 * 
	 * @Title: selectArticle 
	 * @Description: 根据id查看文章详情
	 * @param id
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selectArticle")
	@ResponseBody
	public Object selectArticle(String id) {
		Article article = articleService.selectArticle(id);
		if(article.getContent_type()==ContentType.IMAGE) {
			String content = article.getContent();
			List<Content> contentList = JSON.parseArray(content, Content.class);
			StringBuffer sb=new StringBuffer();
			for (Content content2 : contentList) {
				sb.append("<img src='/pic/"+content2.getPictrue()+"' class='d-block w-100' alt='...'><br>"+content2.getMessage()+"<br>");
			}
			article.setContent(sb.toString());
		}
		return article;
	}

	/**
	 * 
	 * @Title: updateStatus 
	 * @Description: 修改文章状态,审核文章（同时发送消息至kafka，更新es数据）
	 * @param status
	 * @param id
	 * @return
	 * @return: Object
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public Object updateStatus(String status, String id) {
		int i = articleService.updateStatus(status, id);
		kafka.send("article", "audit="+id);
		return i >= 1 ? true : false;
	}

	/**
	 * 
	 * @Title: selectArticleList 
	 * @Description: 个人中心的查看文章
	 * @param m
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selectArticleList")
	public Object selectArticleList(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null) {
			article.setUser_id(user.getId());
		}
		PageInfo<Article> page = articleService.getstatusList(article, pageNum,
				pageSize);

		m.addAttribute("page", page);
		m.addAttribute("title", article.getTitle());
		m.addAttribute("status", article.getStatus());
		m.addAttribute("list", page.getList());
		return "my/article";
	}

	/**
	 * 
	 * @Title: toAdd 
	 * @Description: 去发布文章页面
	 * @return
	 * @return: Object
	 */
	@RequestMapping("toAdd")
	public Object toAdd() {
		return "my/release";
	}
	/**
	 * 
	 * @Title: channelList 
	 * @Description: 查询栏目列表
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("channelList")
	public Object channelList() {
		List<Channel> list=channelService.channelList();
		return list;
	}
	/**
	 * 
	 * @Title: categoryList 
	 * @Description: 查询分类列表
	 * @param id
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("categoryList")
	public Object categoryList(String id) {
		List<Category> list=channelService.categoryList(id);
		return list;
	}
	/**
	 * 
	 * @Title: add 
	 * @Description: 个人中心文章发布
	 * @param article
	 * @param file
	 * @param session
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("add")
	public Object add(Article article,String content,@RequestParam("file")MultipartFile file,HttpSession session) {
		User user = (User)(session.getAttribute("user"));
		if(null!=user) {
			article.setUser_id(user.getId());
		}
		article.setContext(content);
		try {
			if(file.getSize()>0) {
				//上传图片的路劲
				String path="e:/pic/";
				//获得上传图片的名称
				String originalFilename = file.getOriginalFilename();
				//获得后缀
				String endName = originalFilename.substring(originalFilename.lastIndexOf("."));
				//获得新的文件名称
				String fileName = UUID.randomUUID().toString()+endName;
				//创建上传的文件
				File file2 = new File(path+fileName);
				
				file.transferTo(file2);
				article.setPicture(fileName);
				
			}
			article.setContent_type(ContentType.HTML);
			articleService.add(article);
			return true;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @Title: toAddImage 
	 * @Description: 前往发布图片界面
	 * @return
	 * @return: Object
	 */
	@RequestMapping("toAddImage")
	public Object toAddImage() {
		return "my/addImage";
	}
	/**
	 * 
	 * @Title: addImage 
	 * @Description: 新增图片
	 * @param article
	 * @param files
	 * @return
	 * @return: Object
	 */
	@ResponseBody
	@RequestMapping("addImage")
	public Object addImage(Article article,@RequestParam("file")MultipartFile file,MultipartFile[] myFiles,String[] myMessages) {
		try {
			if(file.getSize()>0) {
				String path="e:/pic/";
				String originalFilename = file.getOriginalFilename();
				String endName = originalFilename.substring(originalFilename.lastIndexOf("."));
				String newName = UUID.randomUUID().toString()+endName;
				File files = new File(path+newName);
				file.transferTo(files);
				article.setPicture(newName);
			}
			List<Content> list=new ArrayList<Content>();
			int i=0;
			for (MultipartFile myFile : myFiles) {
				if(myFile.getSize()>0) {
					//上传文件名
					String originalFilename = myFile.getOriginalFilename();
					//图片存放位置
					String path="e:/pic/";
					//上传文件的随机名
					String startName = UUID.randomUUID().toString();
					//获取上传文件后缀
					String endName = originalFilename.substring(originalFilename.lastIndexOf("."));
					//创建上传文件
					File fileOne=new File(path+startName+endName);
					//在指定位置创建文件
					myFile.transferTo(fileOne);
					Content content=new Content(startName+endName, myMessages[i]);
					list.add(content);
				}
				i++;
			}
			article.setContent(JSON.toJSONString(list));
			article.setContent_type(ContentType.IMAGE);
			articleService.add(article);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Title: search 
	 * @Description: 用elasticsearch完成搜素
	 * @param m
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("search")
	public Object search(Model m,String key,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		
		List<Article> articleSearch = articleRepository.findByTitle(key);
		
		PageInfo<Article> info=(PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, pageNum, pageSize, new String[]{"title"}, "id", key);
		m.addAttribute("key", key);
		m.addAttribute("articleSearch", info);
		return "index/index";
	}
	
	
}
