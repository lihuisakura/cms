package com.lihui.cms.dao;

import java.util.List;

import com.lihui.cms.domain.Comment;

public interface CommentDao {

	List<Comment> findCommentList(Comment comment);

	void addComment(Comment comment);
}
