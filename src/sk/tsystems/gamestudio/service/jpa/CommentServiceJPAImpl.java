package sk.tsystems.gamestudio.service.jpa;

import java.util.List;

import sk.ness.jpa.JpaHelper;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.service.CommentService;

public class CommentServiceJPAImpl implements CommentService {

	@Override
	public void add(Comment comment) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(comment);
		JpaHelper.commitTransaction();
	}

	@Override
	public List<Comment> findCommentForGame(String game) {
		return JpaHelper.getEntityManager().createQuery("SELECT c FROM comment c WHERE c.game=:game")
				.setParameter("game", game).getResultList();
	}

}
