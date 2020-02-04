package com.lihui.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.cms.dao.SlideDao;
import com.lihui.cms.domain.Slide;
import com.lihui.cms.service.SlideService;
@Service
public class SlideServiceImpl implements SlideService {

	
	@Autowired
	private SlideDao slideDao;

	@Override
	public List<Slide> slideList() {
		// TODO Auto-generated method stub
		return slideDao.slideList();
	}
}
