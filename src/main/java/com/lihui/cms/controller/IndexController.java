package com.lihui.cms.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.domain.Comment;
import com.lihui.cms.domain.Content;
import com.lihui.cms.domain.enums.ContentType;
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
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ThreadPoolTaskExecutor executor;
	@Autowired
	private KafkaTemplate kafka;
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
	@SuppressWarnings("unchecked")
	@RequestMapping("select")
	public Object select(HttpServletRequest request,Model m,String id,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		//点击量增加
		//利用Redis提高性能，当用户浏览文章时，将“Hits_${文章ID}_${用户IP地址}”为key，
		String remoteAddr = request.getRemoteAddr();
		String key="Hits_"+id+"_"+remoteAddr;
		//查询Redis里有没有该key，如果有key，则不做任何操作。
		Boolean hasKey = redisTemplate.hasKey(key);
		//如果没有，则使用Spring线程池异步执行数据库加1操作，
		if(!hasKey) {
			//executor.execute(new Runnable() {
				//@Override
				//public void run() {
					//点击量加一
					//articleService.addHits(id);
					//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，
					//redisTemplate.opsForValue().set(key, null);
					//而且有效时长为5分钟。
					//redisTemplate.expire(key, 5, TimeUnit.MINUTES);
				//}
			//});
			//利用Kafka进行流量削峰。
			//当用户浏览文章时，往Kafka发送文章ID，
			kafka.send("article", "xiaofeng="+id);
			//在消费端获取文章ID，再执行数据库加1操作
			//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，
			redisTemplate.opsForValue().set(key, null);
			//而且有效时长为5分钟。
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		}
		
		
		
		
		//根据文章id查文章详情
		Article article=articleService.selectArticle(id);
		if(article.getContent_type()==ContentType.IMAGE) {
			String content = article.getContent();
			List<Content> imageContent = JSON.parseArray(content, Content.class);
			m.addAttribute("imageContent", imageContent);
		}
		m.addAttribute("article", article);
		
		Article relevant=new Article();
		relevant.setChannel_id(article.getChannel_id());
		//最新文章
		//relevant.setCategory_id(article.getCategory_id());
		relevant.setContent_type(article.getContent_type());
		PageInfo<Article> pageInfo=articleService.getArticleList(relevant,1,3);
		m.addAttribute("relevantArticle", pageInfo.getList());
		
		
		//该文章的栏目
		Channel channel=channelService.getChannel(article.getChannel_id());
		m.addAttribute("articleChannel", channel);
		return "index/article";
	}
}
