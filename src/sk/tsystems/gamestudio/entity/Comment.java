package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private int ident;
	private String player;
	private String game;
	private String comment;

	public Comment(String player, String game, String comment) {
		this.player = player;
		this.game = game;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Game: ");
		sb.append(this.getGame());
		sb.append(", comment: ");
		sb.append(this.getComment());
		sb.append(", from player: ");
		sb.append(this.getPlayer());
		return sb.toString();
	}
}
