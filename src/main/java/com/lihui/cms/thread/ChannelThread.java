package com.lihui.cms.thread;

import java.util.List;

import org.springframework.ui.Model;

import com.lihui.cms.domain.Article;
import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.service.ChannelService;
/**
 * 
 * @ClassName: ChannelThread 
 * @Description: 一条线程获取栏目和分类，存入作用域中
 * @author:lh 
 * @date: 2020年2月18日 上午11:03:00
 */
public class ChannelThread implements Runnable{

	
	private Model m;
	private ChannelService channelService;
	private Article article;
	
	
	public ChannelThread(Model m, ChannelService channelService) {
		super();
		this.m = m;
		this.channelService = channelService;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Channel> list=channelService.channelList();
		m.addAttribute("channelList", list);
		
	}

}
