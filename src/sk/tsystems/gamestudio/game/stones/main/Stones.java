package sk.tsystems.gamestudio.game.stones.main;

import sk.tsystems.gamestudio.game.stones.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.stones.core.Field;

public class Stones {

	private long startTime;
	private static Stones instance;

	public Stones() {
		instance = this;

		ConsoleUI start = new ConsoleUI();

		Field field = new Field(4, 4);

		startTime = System.currentTimeMillis();

		start.newGameStarted(field);
	}

	public int getTime() {
		return (int) (System.currentTimeMillis() - startTime) / 1000;
	}

	public static Stones getInstance() {
		return instance;
	}

//	public static void main(String[] args) {
//		new Kamene();
//	}
	
}
