package com.lihui.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.lihui.cms.domain.Favorite;

public interface FavoriteDao {

	List<Favorite> findFavorite(Favorite favorite);

	@Delete("delete from cms_favorite where id=#{id}")
	int deleteFavorite(@Param("id")String id);

	void add(Favorite favorite);

	int findOneFavorite(@Param("url")String url,@Param("user_id")Integer user_id);

}
