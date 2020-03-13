package com.lihui.cms.thread;

import java.util.List;

import org.springframework.ui.Model;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.enums.ContentType;
import com.lihui.cms.service.ArticleService;
/**
 * 
 * @ClassName: NewImageThread 
 * @Description: 一条线程获取右侧“最新图片”，存入作用域中
 * @author:lh 
 * @date: 2020年2月18日 上午11:03:20
 */
public class NewImageThread implements Runnable {

	private Article article;
	private Integer pageNum;
	private Integer pageSize;
	private ArticleService articleService;
	private Model m;
	
	
	public NewImageThread(Article article, Integer pageNum, Integer pageSize, ArticleService articleService, Model m) {
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

		article.setHot(0);
		article.setContent_type(ContentType.IMAGE);
		PageInfo<Article> newImage=articleService.getArticleList(article, 1, 10);
		m.addAttribute("newImage", newImage.getList());
	}

}
