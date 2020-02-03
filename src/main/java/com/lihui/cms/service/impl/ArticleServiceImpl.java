package com.lihui.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.dao.ArticleDao;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public PageInfo<Article> getArticleList(String title, Integer status, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<Article> list=articleDao.getArticleList(title,status);
		return new PageInfo<Article>(list);
	}

	@Override
	public Article selectArticle(String id) {
		// TODO Auto-generated method stub
		return articleDao.selectArticle(id);
	}

	@Override
	public int updateStatus(String status, String id) {
		// TODO Auto-generated method stub
		return articleDao.updateStatus(status,id);
	}
}
