package com.lihui.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihui.cms.dao.FavoriteDao;
import com.lihui.cms.domain.Favorite;
import com.lihui.cms.service.FavoriteService;
@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteDao favoriteDao;

	@Override
	public PageInfo<Favorite> findFavorite(Favorite favorite, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Favorite> list=favoriteDao.findFavorite(favorite);
		return new PageInfo<Favorite>(list);
	}

	@Override
	public boolean deleteFavorite(String id) {
		// TODO Auto-generated method stub
		return favoriteDao.deleteFavorite(id)>0;
	}

	@Override
	public void add(Favorite favorite) {
		// TODO Auto-generated method stub
		favoriteDao.add(favorite);
	}

	@Override
	public boolean findOneFavorite(String url,Integer user_id) {
		// TODO Auto-generated method stub
		return favoriteDao.findOneFavorite(url,user_id)>0;
	}
	
}
