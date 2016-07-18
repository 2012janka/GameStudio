package sk.tsystems.gamestudio.service.jpa;

import java.util.List;

import sk.ness.jpa.JpaHelper;
import sk.tsystems.gamestudio.entity.CommentFromPlayer;
import sk.tsystems.gamestudio.service.CommentService;

public class CommentServiceJPAImpl implements CommentService {

	@Override
	public void add(CommentFromPlayer comment) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(comment);
		JpaHelper.commitTransaction();
	}

	@Override
	public List<CommentFromPlayer> findCommentForGame(String game) {
		return JpaHelper.getEntityManager().createQuery("SELECT c FROM CommentFromPlayer c WHERE c.game=:game")
				.setParameter("game", game).getResultList();
	}

}
