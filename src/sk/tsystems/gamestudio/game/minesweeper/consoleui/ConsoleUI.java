package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.game.minesweeper.UserInterface;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		boolean playing = true;
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {
				System.out.println("You WIN!");
				playing = false;
			} else if (field.getState() == GameState.FAILED) {
				System.out.println("You LOOSE!");
				playing = false;
			}
		} while (playing);
	}

	@Override
	public void update() {
		System.out.println("Time: " + Minesweeper.getInstance().getPlayingSeconds());
		System.out.print(field.getRemainingMineCount() + " |");
		for (int i = 0; i < field.getRowCount(); i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.print("____________________");
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.println();
			System.out.printf("%c |", ((char) (row + 'A')));
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile actualTile = field.getTile(row, column);
				if (actualTile.getState() == Tile.State.OPEN && actualTile instanceof Mine) {
					System.out.print("X ");
				} else if (actualTile.getState() == Tile.State.OPEN && actualTile instanceof Clue) {
					System.out.print(((Clue) actualTile).getValue() + " ");
				} else if (actualTile.getState() == Tile.State.MARKED) {
					System.out.print("M ");
				} else if (actualTile.getState() == Tile.State.CLOSED) {
					System.out.print("- ");
				}
			}
		}
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		System.out.println();
		System.out.println("Please enter your selection <X> EXIT, <MA1> MARK, <OB4> OPEN : ");
		try {
			String value = readLine().toLowerCase();
			handleInput(value);
		} catch (WrongFormatException ex) {
			System.out.println(ex.getMessage());
		}
	}

	void handleInput(String input) throws WrongFormatException {
		Pattern INPUT_PATTERN = Pattern.compile("x|(m|o)([a-i])([0-8])");
		Matcher matcher = INPUT_PATTERN.matcher(input);

		if (matcher.matches()) {
			String option1 = matcher.group(1);
			String option2 = matcher.group(2);
			String option3String = matcher.group(3);

			if (input.equals("x")) {
				System.out.println("Bye :-)");
				System.exit(0);
			} else if (option1.equals("m")) {
				int charAI = option2.charAt(0) - 'a';
				int option3Int = Integer.parseInt(option3String);
				field.markTile(charAI, option3Int);

			} else if (option1.equals("o")) {
				int charAI = option2.charAt(0) - 'a';
				int option3Int = Integer.parseInt(option3String);
				field.openTile(charAI, option3Int);
			}
		} else {
			throw new WrongFormatException("WRONG INPUT!");
		}
	}
}
