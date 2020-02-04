package com.lihui.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;
import com.lihui.cms.service.ChannelService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("select")
	public Object select(Model m,String id) {
		Article article=articleService.selectArticle(id);
		m.addAttribute("article", article);
		return "index/article";
	}
}
