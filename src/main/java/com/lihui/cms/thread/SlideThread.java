package com.lihui.cms.thread;

import java.util.List;

import org.springframework.ui.Model;

import com.lihui.cms.domain.Slide;
import com.lihui.cms.service.SlideService;
/**
 * 
 * @ClassName: SlideThread 
 * @Description: 一条线程获取顶部幻灯片数据，存入作用域中
 * @author:lh 
 * @date: 2020年2月18日 上午11:05:39
 */
public class SlideThread implements Runnable {

	private Model m;
	private SlideService slideService;
	
	
	public SlideThread(Model m, SlideService slideService) {
		super();
		this.m = m;
		this.slideService = slideService;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		List<Slide> slides=slideService.slideList();
		m.addAttribute("slides", slides);
	}

}
