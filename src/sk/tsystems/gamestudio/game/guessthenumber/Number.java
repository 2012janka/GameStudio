package sk.tsystems.gamestudio.game.guessthenumber;
import java.util.Random;

public class Number {
	private int number;
	private boolean win;

	public Number() {
		Random random = new Random();
		this.number = random.nextInt(10);
		this.win = false;
	}

	public int getNumber() {
		return number;
	}

	public boolean isWin() {
		return win;
	}

	public void guessNumber(int guess) {
		if (guess == this.number) {
			win = true;
		} else if (guess < this.number) {
			System.out.println("Try a larger number :-) ");
		} else {
			System.out.println("Try a smaller number :-) ");
		}
	}
}