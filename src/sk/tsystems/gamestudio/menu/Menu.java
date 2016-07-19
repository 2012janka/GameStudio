package sk.tsystems.gamestudio.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sk.tsystems.gamestudio.entity.CommentFromPlayer;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.stones.main.Stones;
import sk.tsystems.gamestudio.game.guessthenumber.ConsoleUI;
import sk.tsystems.gamestudio.game.guessthenumber.GuessTheNumber;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.jdbc.CommentServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jdbc.RatingServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jdbc.ScoreServiceJDBCImpl;
import sk.tsystems.gamestudio.service.jpa.CommentServiceJPAImpl;
import sk.tsystems.gamestudio.service.jpa.RatingServiceJPAImpl;
import sk.tsystems.gamestudio.service.jpa.ScoreServiceJPAImpl;

public class Menu {
	// private String gameName;
	// private String playerName = System.getProperty("user.home");
	private String playerName = "Stano";
	private CommentService comments;
	private RatingService rating;
	private ScoreService score;

	// public String getPlayerName() {
	// return playerName;
	// }

	public Menu() {
		comments = new CommentServiceJPAImpl();
		rating = new RatingServiceJPAImpl();
		score = new ScoreServiceJPAImpl();
	}

	// public void setPlayerName(String playerName) {
	// this.playerName = playerName;
	// }

	// public String getGameName() {
	// return gameName;
	// }

	// public void setGameName(String gameName) {
	// this.gameName = gameName;
	// }

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private enum Option {
		GUESS_THE_NUMBER, MINESWEEPER, STONES
	};

	public void run() {
		System.out.println("Which game would you like to play? ");
		while (true) {
			switch (showMenu()) {
			case GUESS_THE_NUMBER:
				process("GUESS_THE_NUMBER");
				break;
			case MINESWEEPER:
				process("MINESWEEPER");
				break;
			case STONES:
				process("STONES");
				break;
			default:
				System.out.println("Wrong input!");
			}
		}
	}

	private CommentFromPlayer newComment(String game) {

		System.out.println("Do you want to add comment for this game? y/yes, n/no ");
		String option = readLine();

		switch (option) {
		case "y":
			System.out.println("Write your comment: ");
			String playerComment = readLine().trim();					//ked dam iba enter nema mi pridat komentar
			// System.out.println(playerComment);
			CommentFromPlayer comment = new CommentFromPlayer(playerName, game, playerComment);
			return comment;

		case "n":
			break;

		default:
			System.out.println("Wrong input!");
		}
		return null;
	}

	private Rating newRating(String game) {
		boolean rate = true;

		System.out.println("\nDo you want to rate this game? y/yes, n/no ");
		String option = readLine();

		switch (option) {
		case "y":
			while (rate) {
				System.out.println("Choose rating from 1 to 5: ");
				int playerRating = Integer.parseInt(readLine());
				if (playerRating <= 5 && playerRating >= 1) {
					// System.out.println(playerRating);
					rate = false;
					Rating rating = new Rating(playerName, game, playerRating);
					return rating;
				} else {
					System.out.println("You can rate only from 1 to 5!");
				}
			}
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

	private void process(String game) {
		// setGameName("game");
		CommentFromPlayer comm = newComment(game);
		if (comm != null) {
			comments.add(comm);
		}
		comments.findCommentForGame(game).toString();
		System.out.println("\nAverage rating: " + rating.getAverageRating(game) + "; number of ratings: "
				+ rating.getNumberOfRatings(game) + "\n");

		if (game == "GUESS_THE_NUMBER") {
			new GuessTheNumber();
		}
		if (game == "MINESWEEPER") {
			new Minesweeper();
		}
		if (game == "STONES") {
			new Stones();
		}

		Rating rate = newRating(game);
		if (rate != null) {
			rating.deleteOldRating(rate);
			rating.add(rate);
		}

		System.out.println("TOP SCORES: " + score.findTopScoreForGame(game).toString());
	}
}
