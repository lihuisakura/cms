package com.lihui.cms.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.lihui.cms.domain.Comment;

public interface CommentDao {

	List<Comment> findCommentList(Comment comment);

	void addComment(Comment comment);

	int findCommentNum(@RequestParam("id")String id);
}
