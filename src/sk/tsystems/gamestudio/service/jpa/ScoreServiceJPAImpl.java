package sk.tsystems.gamestudio.service.jpa;

import java.util.List;

import sk.ness.jpa.JpaHelper;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;

public class ScoreServiceJPAImpl implements ScoreService{

	@Override
	public void add(Score score) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(score);
		JpaHelper.commitTransaction();
		
	}

	@Override
	public List<Score> findTopScoreForGame(String game) {
		return JpaHelper.getEntityManager().createQuery("SELECT s FROM Score s WHERE s.game=:game").setParameter("game", game).setMaxResults(10).getResultList();
	}

}
