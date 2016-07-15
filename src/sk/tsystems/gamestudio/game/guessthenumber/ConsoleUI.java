package sk.tsystems.gamestudio.game.guessthenumber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI {
	Number number;

	public ConsoleUI(Number number) {
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
		int numberOfGuesses = 0;
		do {
			numberOfGuesses++;
			number.guessNumber(processInput());
			if (number.isWin()) {
				System.out.println("You win! You guessed the number after " + numberOfGuesses + " attempts!");
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
}