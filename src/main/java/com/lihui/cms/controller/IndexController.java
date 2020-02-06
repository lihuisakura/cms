package com.lihui.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.domain.Comment;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;
import com.lihui.cms.service.CommentService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private CommentService commentService;
	/**
	 * 
	 * @Title: select 
	 * @Description: 今日文章详情页，内部包含：文章内容，评论，最新文章
	 * @param m
	 * @param id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("select")
	public Object select(Model m,String id,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		//点击量增加
		articleService.addHits(id);
		//根据文章id查文章详情
		Article article=articleService.selectArticle(id);
		m.addAttribute("article", article);
		
		Article relevant=new Article();
		relevant.setChannel_id(article.getChannel_id());
		//最新文章
		//relevant.setCategory_id(article.getCategory_id());
		PageInfo<Article> pageInfo=articleService.getArticleList(relevant,1,3);
		m.addAttribute("relevantArticle", pageInfo.getList());
		//该文章的栏目
		Channel channel=channelService.getChannel(article.getChannel_id());
		m.addAttribute("articleChannel", channel);
		return "index/article";
	}
}
