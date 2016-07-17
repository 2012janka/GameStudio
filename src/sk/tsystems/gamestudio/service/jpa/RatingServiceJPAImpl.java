package sk.tsystems.gamestudio.service.jpa;

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
	public int getAverageRating(String game) {
		return JpaHelper.getEntityManager().createQuery("SELECT AVG(r.rating) FROM rating r WHERE r.game=:game").setParameter("game", game).getFirstResult();
	}

	@Override
	public int getNumberOfRatings(String game) {
		return JpaHelper.getEntityManager().createQuery("SELECT COUNT(r) FROM rating r WHERE r.game=:game").setParameter("game", game).getFirstResult();
	}

}
