package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Score;

public interface ScoreService {
	void add(Score score);

	List<Score> findTopScoreForGame(String game);
}
