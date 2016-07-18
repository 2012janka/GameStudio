package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score {

	@Id
	@GeneratedValue
	private int ident;
	private String player;
	private String game;
	private int score;

	public Score(String player, String game, int score) {
		this.player = player;
		this.game = game;
		this.score = score;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("score ");
		sb.append(this.getScore());
		sb.append(" from player ");
		sb.append(this.getPlayer());
		return sb.toString();
	}

}
