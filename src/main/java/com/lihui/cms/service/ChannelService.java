package com.lihui.cms.service;

import java.util.List;

import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;

public interface ChannelService {

	List<Channel> channelList();

	List<Category> categoryList(String id);

	

}
