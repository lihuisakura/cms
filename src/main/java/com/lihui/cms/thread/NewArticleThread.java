package com.lihui.cms.thread;

import org.springframework.ui.Model;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;
/**
 * 
 * @ClassName: NewArticleThread 
 * @Description: 一条线程获取右侧最新文章，存入作用域中
 * @author:lh 
 * @date: 2020年2月18日 上午11:03:35
 */
public class NewArticleThread implements Runnable {

	private Article article;
	private Integer pageNum;
	private Integer pageSize;
	private ArticleService articleService;
	private Model m;
	
	
	public NewArticleThread(Article article, Integer pageNum, Integer pageSize, ArticleService articleService,
			Model m) {
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

		PageInfo<Article> newArticle=articleService.getArticleList(article, pageNum, 3);
		m.addAttribute("newArticle", newArticle.getList());
	}

}
