package sk.tsystems.gamestudio.game.stones.consoleui;

import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.game.stones.core.Field;
import sk.tsystems.gamestudio.game.stones.core.GameState;
import sk.tsystems.gamestudio.game.stones.core.Tile;
import sk.tsystems.gamestudio.game.stones.main.Kamene;

public class ConsoleUI {
	private Field field;

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void newGameStarted(Field field) {
		this.field = field;
		boolean playing = true;
		do {
			update();
			processInput();
			save();

			if (field.getState() == GameState.NEW) {
				new Kamene();
			}

			field.setGameState(GameState.PLAYING);

			if (field.getState() == GameState.EXIT) {
				//update();
				System.out.println("BYE");
				playing = false;
			}
			
			if (field.getState() == GameState.SOLVED) {
				update();
				System.out.println("You WIN!");
				playing = false;
			}
			
			
		} while (playing);
	}

	public void update() {
		int numberOfTiles = field.getColumnCount() * field.getRowCount();

		System.out.println("Time: " + Kamene.getInstance().getTime());

		for (int m = 0; m < field.getRowCount(); m++) {
			System.out.println();
			for (int n = 0; n < field.getColumnCount(); n++) {
				Tile aktTile = field.getTile(m, n);
				if (aktTile.getValue() == numberOfTiles) { // System.out.printf("%2d
															// ",
															// aktTile.getValue())
															// //zarovnanie
					System.out.print("   ");
				} else if (aktTile.getValue() < 10) {
					System.out.print(" " + aktTile.getValue() + " ");
				} else {
					System.out.print(aktTile.getValue() + " ");
				}
			}
		}
	}

	private void processInput() {
		System.out.println("\n");
		System.out.println(
				"Please enter your selection <new> NEW GAME, <exit> EXIT, <w/up> UP, <s/down> DOWN, <a/left> LEFT, <d/right> RIGHT : ");
		try {
			String value = readLine().toLowerCase().trim();
			handleInput(value);
		} catch (WrongFormatException ex) {
			System.out.println(ex.getMessage());
		}
	}

	void handleInput(String input) throws WrongFormatException {
		Pattern INPUT_PATTERN = Pattern.compile("new|exit|w|up|s|down|a|left|d|right");
		Matcher matcher = INPUT_PATTERN.matcher(input);
		int row = field.getEmptyTile()[0];
		int column = field.getEmptyTile()[1];

		if (matcher.matches()) {
			if (input.equals("new")) {
				field.setGameState(GameState.NEW);
				System.out.println("NEW GAME");
			} else if (input.equals("exit")) {
				field.setGameState(GameState.EXIT);
				System.out.println("Your time: " + Kamene.getInstance().getTime());
			} else if (field.borders()[0] && (input.equals("w") || input.equals("up"))) {
				if (field.borders()[0]) {
					Tile change = field.getTile(row + 1, column);
					swap(change, field.getTile(row, column));
				} else {
					throw new WrongFormatException("TRY ANOTHER WAY.");
				}
			} else if (input.equals("s") || input.equals("down")) {
				if (field.borders()[1]) {
					Tile change = field.getTile(row - 1, column);
					swap(change, field.getTile(row, column));
				} else {
					throw new WrongFormatException("TRY ANOTHER WAY.");
				}
			} else if (input.equals("a") || input.equals("left")) {
				if (field.borders()[2]) {
					Tile change = field.getTile(row, column + 1);
					swap(change, field.getTile(row, column));
				} else {
					throw new WrongFormatException("TRY ANOTHER WAY.");
				}
			} else if (input.equals("d") || input.equals("right")) {
				if (field.borders()[3]) {
					Tile change = field.getTile(row, column - 1);
					swap(change, field.getTile(row, column));
				} else {
					throw new WrongFormatException("TRY ANOTHER WAY.");
				}
			}
		} else {
			throw new WrongFormatException("WRONG INPUT");
		}
	}

	private void swap(Tile tile1, Tile tile2) {
		int tmp = tile1.getValue();
		tile1.setValue(tile2.getValue());
		tile2.setValue(tmp);
	}

	public void save() {
		try {
			FileOutputStream out = new FileOutputStream("kamene.bin");
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(field);
			s.close();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	// public static Field load() {
	// try {
	// FileInputStream in = new FileInputStream("kamene.bin");
	// ObjectInputStream s = new ObjectInputStream(in);
	//
	// this.field = (Field) s.readObject();
	//
	// s.close();
	//
	// } catch (FileNotFoundException ex) {
	// System.out.println(ex.getMessage());
	// } catch (IOException ex) {
	// System.out.println(ex.getMessage());
	// } catch (ClassNotFoundException ex) {
	// System.out.println(ex.getMessage());
	// }
	// }

}
