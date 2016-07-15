package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Rating;

public interface RatingService {
	void add(Rating rating);

	int getAverageRating(String game);
	
	int getNumberOfRatings(String game);
}
