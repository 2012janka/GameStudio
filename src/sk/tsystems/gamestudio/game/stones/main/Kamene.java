package sk.tsystems.gamestudio.game.stones.main;

import sk.tsystems.gamestudio.game.stones.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.game.stones.core.Field;

public class Kamene {

	private long startTime;
	private static Kamene instance;

	public Kamene() {
		instance = this;

		ConsoleUI start = new ConsoleUI();

		Field field = new Field(2, 2);

		startTime = System.currentTimeMillis();

		start.newGameStarted(field);
	}

	public int getTime() {
		return (int) (System.currentTimeMillis() - startTime) / 1000;
	}

	public static Kamene getInstance() {
		return instance;
	}

//	public static void main(String[] args) {
//		new Kamene();
//	}
	
}
