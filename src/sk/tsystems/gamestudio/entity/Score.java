package sk.tsystems.gamestudio.entity;

public class Score {
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

}
