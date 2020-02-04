package com.lihui.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lihui.cms.domain.Category;
import com.lihui.cms.domain.Channel;

public interface ChannelDao {

	List<Channel> channelList();

	List<Category> selectCategory(@Param("id")String id);

}
