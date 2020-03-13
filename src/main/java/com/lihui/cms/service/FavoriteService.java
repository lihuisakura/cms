package com.lihui.cms.service;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Favorite;

public interface FavoriteService {

	PageInfo<Favorite> findFavorite(Favorite favorite, Integer pageNum, Integer pageSize);

	boolean deleteFavorite(String id);

	void add(Favorite favorite);

	boolean findOneFavorite(String url, Integer user_id);

}
