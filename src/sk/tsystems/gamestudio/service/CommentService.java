package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

public interface CommentService {
	void add(Comment comment);

	List<Comment> findCommentForGame(String game);
}
