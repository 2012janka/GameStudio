package sk.tsystems.gamestudio.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.ness.jpa.JpaHelper;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.service.RatingService;

public class RatingServiceJPAImpl implements RatingService {

	@Override
	public void add(Rating rating) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(rating);
		JpaHelper.commitTransaction();

	}

	@Override
	public double getAverageRating(String game) {
		EntityManager em = JpaHelper.getEntityManager();
		Query q = em.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game").setParameter("game", game);
		Double actual = (Double) q.getSingleResult();
		if (actual == null) {
			return 0;
		} else {
			return actual;
		}
	}

	@Override
	public int getNumberOfRatings(String game) {
		EntityManager em = JpaHelper.getEntityManager();
		Query q = em.createQuery("SELECT COUNT(r) FROM Rating r WHERE r.game=:game")
				.setParameter("game", game);
		return Integer.parseInt(q.getSingleResult().toString());
//		return JpaHelper.getEntityManager().createQuery("SELECT COUNT(r) FROM Rating r WHERE r.game=:game")
//				.setParameter("game", game).getSingleResult();
	}

	@Override
	public void deleteOldRating(Rating rating) {
		JpaHelper.getEntityManager().remove(r.rating=:rating);			
	}
}
