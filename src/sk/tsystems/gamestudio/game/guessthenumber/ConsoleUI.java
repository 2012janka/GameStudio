package sk.tsystems.gamestudio.game.guessthenumber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.jdbc.ScoreServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jpa.ScoreServiceJPAImpl;

public class ConsoleUI {
	private ScoreService score;
	private Number number;
	private int numberOfGuesses;

	public int getNumberOfGuesses() {
		return numberOfGuesses;
	}
	
	public ConsoleUI(Number number) {
		score = new ScoreServiceJPAImpl();
		this.number = number;
	}

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private int readLine() {
		try {
			return Integer.parseInt(input.readLine());
		} catch (IOException e) {
			return 0;
		}
	}

	public void newGameStarted() {
		System.out.println("Hello! Guess the number from 1 to 1000!");
		boolean playing = true;
		do {
			this.numberOfGuesses++;
			number.guessNumber(processInput());
			if (number.isWin()) {
				System.out.println("You win! You guessed the number after " + this.numberOfGuesses + " attempts!");
				addScoreToDatabase();
				playing = false;
			}
		} while (playing);
	}

	private int processInput() {
		System.out.println("Please enter your guess: ");
		int value = readLine();
		if (value > 1000 || value <= 0) {
			System.out.println("Wrong input!");
			return 0;
		} else {
			return value;
		}
	}
	
	private void addScoreToDatabase(){
		Score newScore=new Score("Jano", "GUESS_THE_NUMBER", getNumberOfGuesses());
		score.add(newScore);		
	}
}