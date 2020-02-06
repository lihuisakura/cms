package com.lihui.cms.service;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Link;

public interface LinkService {

	PageInfo<Link> selects(Link link, Integer pageNum, Integer pageSize);

	Link selectLinkID(String id);

	boolean update(Link link);

}
