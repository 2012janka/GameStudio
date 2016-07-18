package sk.tsystems.gamestudio.game.stones.main;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.stones.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.stones.core.Field;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.jdbc.ScoreServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jpa.ScoreServiceJPAImpl;

public class Stones {

	private long startTime;
	private static Stones instance;
	private ScoreService score;

	public Stones() {
		instance = this;

		score = new ScoreServiceJPAImpl();

		ConsoleUI start = new ConsoleUI();

		Field field = new Field(2, 2);

		startTime = System.currentTimeMillis();

		start.newGameStarted(field);
	}

	public int getTime() {
		return (int) (System.currentTimeMillis() - startTime) / 1000;
	}

	public static Stones getInstance() {
		return instance;
	}

	public void addScoreToDatabase(){
		Score newScore	=	new Score("Jano", "STONES", getTime());
		score.add(newScore);		
	}

	// public static void main(String[] args) {
	// new Kamene();
	// }

}
