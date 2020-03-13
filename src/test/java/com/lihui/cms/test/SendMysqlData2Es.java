package com.lihui.cms.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.dao.ArticleRepository;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class SendMysqlData2Es {

	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleRepository articleRepository;
	
	@Test
	public void sendMysqlData2Es() {
		PageInfo<Article> getstatusList = articleService.getstatusList(new Article(),1,1000);
		List<Article> list= getstatusList.getList();
		articleRepository.saveAll(list);
	}
}
