package com.lihui.cms.dao;

import java.util.List;



import com.lihui.cms.domain.Link;

public interface LinkDao {

	List<Link> selects(Link link);

	Link selectLinkID(String id);

	
	int update(Link link);

}
