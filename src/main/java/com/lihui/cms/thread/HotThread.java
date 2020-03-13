package com.lihui.cms.thread;

import org.springframework.ui.Model;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;
/**
 * 
 * @ClassName: HotThread 
 * @Description: 一条线程获取首页热门文章，存入作用域中
 * @author:lh 
 * @date: 2020年2月18日 上午11:03:59
 */
public class HotThread implements Runnable{

	
	private Article article;
	private Integer pageNum;
	private Integer pageSize;
	private ArticleService articleService;
	private Model m;
	
	
	



	public HotThread(Article article, Integer pageNum, Integer pageSize, ArticleService articleService, Model m) {
		super();
		this.article = article;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.articleService = articleService;
		this.m = m;
	}






	@Override
	public void run() {
		// TODO Auto-generated method stub
		article.setHot(1);
		PageInfo<Article> pageInfo=articleService.getArticleList(article, pageNum, 3);
		m.addAttribute("hots", pageInfo);
	}

	
}
