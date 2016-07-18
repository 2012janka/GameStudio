package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.CommentFromPlayer;

public interface CommentService {
	void add(CommentFromPlayer comment);

	List<CommentFromPlayer> findCommentForGame(String game);
}
