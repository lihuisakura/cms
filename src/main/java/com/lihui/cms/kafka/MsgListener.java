package com.lihui.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.lihui.cms.dao.ArticleDao;
import com.lihui.cms.dao.ArticleRepository;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;

public class MsgListener implements MessageListener<String, String>{

	@Autowired
	ArticleDao articleDao;
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		// TODO Auto-generated method stub
		System.err.println("kafka收到了消息");
		String json = data.value();
		if(json.startsWith("audit")) {
			String[] split = json.split("=");
			Article selectArticle = articleService.selectArticle(split[1]);
			articleRepository.save(selectArticle);
			System.err.println("审核通过的文章保存在es数据中......");
		}else if(json.startsWith("xiaofeng")){
			String[] split = json.split("=");
			articleService.addHits(split[1]);
			System.err.println("点击量加一");
		}else {
			Article article = JSON.parseObject(json, Article.class);
			articleDao.add(article);
			System.err.println("保存了文章对象.....");
		}
		
	}

}
