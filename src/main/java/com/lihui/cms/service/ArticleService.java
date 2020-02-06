package com.lihui.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;

public interface ArticleService {

	PageInfo<Article> getArticleList(Article article, Integer pageNum, Integer pageSize);
	
	PageInfo<Article> getstatusList(Article article, Integer pageNum, Integer pageSize);

	Article selectArticle(String id);

	int updateStatus(String status, String id);

	void add(Article article);

	void addHits(String id);


}
