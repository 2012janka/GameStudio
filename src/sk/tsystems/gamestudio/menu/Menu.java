package sk.tsystems.gamestudio.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.game.stones.main.Stones;
import sk.tsystems.gamestudio.game.guessthenumber.GuessTheNumber;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.jdbc.CommentServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jpa.CommentServiceJPAImpl;

public class Menu {
	//private String gameName;
	// private String playerName = System.getProperty("user.home");
	//private String playerName = "Jano";
	private CommentService comments;

//	public String getPlayerName() {
//		return playerName;
//	}

	public Menu() {
		super();

		comments = new CommentServiceJDBCImpl();
	}

//	public void setPlayerName(String playerName) {
//		this.playerName = playerName;
//	}

//	public String getGameName() {
//		return gameName;
//	}

//	public void setGameName(String gameName) {
//		this.gameName = gameName;
//	}

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private enum Option {
		GUESS_THE_NUMBER, MINESWEEPER, STONES
	};

	public void run() {
		System.out.println("Which game would you like to play? ");
		while (true) {
			switch (showMenu()) {
			case GUESS_THE_NUMBER:
				//setGameName("GUESS_THE_NUMBER");
				Comment comm = newComment(Option.GUESS_THE_NUMBER.toString());
				if (comm != null) {
					comments.add(comm);
				}
				comments.findCommentForGame(Option.GUESS_THE_NUMBER.toString());
				new GuessTheNumber();
				break;
			case MINESWEEPER:
				//setGameName("MINESWEEPER");
				Comment comm2 = newComment(Option.MINESWEEPER.toString());
				if (comm2 != null)
					comments.add(comm2);
				comments.findCommentForGame(Option.MINESWEEPER.toString());
				new Minesweeper();
				break;
			case STONES:
				//setGameName("STONES");
				Comment comm3 = newComment(Option.STONES.toString());
				if (comm3 != null)
					comments.add(comm3);
				comments.findCommentForGame(Option.STONES.toString());
				new Stones();
				break;
			}
		}
	}

	private Comment newComment(String game) {

		System.out.println("Do you want to add comment for this game? y/yes, n/no ");
		String option = readLine();

		switch (option) {
		case "y":
			System.out.println("Write your comment: ");
			String playerComment = readLine();
			System.out.println(playerComment);
			Comment comment = new Comment("Jano", Option.valueOf(game).toString(), playerComment);
			return comment;

		case "n":
			break;

		default:
			System.out.println("Wrong input!");
		}
		return null;
	}

	private String readLine() {

		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	private Option showMenu() {
		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			selection = Integer.parseInt(readLine());
		} while (selection <= 0 || selection > Option.values().length);

		return Option.values()[selection - 1];
	}
}
