package com.lihui.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.cms.dao.ChannelDao;
import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;
import com.lihui.cms.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;

	@Override
	public List<Channel> channelList() {
		// TODO Auto-generated method stub
		return channelDao.channelList();
	}

	@Override
	public List<Category> categoryList(String id) {
		// TODO Auto-generated method stub
		return channelDao.selectCategory(id);
	}
	
}
