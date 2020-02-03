package com.lihui.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.lihui.cms.domain.Article;

public interface ArticleDao {

	List<Article> getArticleList(@Param("title")String title, @Param("status")Integer status);

	Article selectArticle(String id);
	@Update("update cms_article set status=#{status} where id=#{id}")
	int updateStatus(@Param("status")String status, @Param("id")String id);

	
}
