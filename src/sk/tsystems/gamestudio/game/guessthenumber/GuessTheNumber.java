package sk.tsystems.gamestudio.game.guessthenumber;

public class GuessTheNumber {

	public GuessTheNumber() {
		Number number = new Number();
		ConsoleUI ui = new ConsoleUI(number);
		ui.newGameStarted();
	}

//	public static void main(String[] args) {
//		new GuessTheNumber();
//	}

}