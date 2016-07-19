package sk.tsystems.gamestudio.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.service.RatingService;

public class RatingServiceJDBCImpl implements RatingService {
	public static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	public static final String USER = "gamestudio";
	public static final String PASSWORD = "gamestudio";

	private Connection con;
	private GamePlayerService id = new GamePlayerService();

	private static final String INSERT_QUERY = "INSERT INTO rating VALUES (?, ?, ?)";
	private static final String GET_IDS = "SELECT GAME_ID, PLAYER_ID from RATING";
	private static final String DELETE_RATING = "DELETE FROM RATING where GAME_ID=? AND PLAYER_ID=?";

	private static final String SELECT_COUNT = "SELECT count(*) FROM RATING r join GAME g on r.GAME_ID = g.GAME_ID where g.NAME=?";
	private static final String SELECT_AVERAGE = "SELECT AVG(r.rating) FROM RATING r join GAME g on r.GAME_ID = g.GAME_ID where g.NAME=?";

	public RatingServiceJDBCImpl() {
		try {
			// Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void checkconn() {
		try {
			if (!con.isValid(2))
				throw new SQLException("aborted");
		} catch (SQLException e1) {
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void add(Rating rating) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, id.findGameID(rating.getGame()));
			stmt.setInt(2, id.findPlayerID(rating.getPlayer()));
			stmt.setLong(3, rating.getRating());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void deleteOldRating(Rating rating) {
		if (!isUnique(rating)) {
			try (PreparedStatement stmt = con.prepareStatement(DELETE_RATING)) {
				stmt.setInt(1, id.findGameID(rating.getGame()));
				stmt.setInt(2, id.findPlayerID(rating.getPlayer()));
				stmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isUnique(Rating rating) {
		try (PreparedStatement stmt = con.prepareStatement(GET_IDS)) {

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == id.findGameID(rating.getGame())
						&& rs.getInt(2) == id.findPlayerID(rating.getPlayer())) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public double getAverageRating(String game) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(SELECT_AVERAGE)) {
			stmt.setString(1, game);
			try (ResultSet rs = stmt.executeQuery()) {
				rs.next();
				return rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getNumberOfRatings(String game) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(SELECT_COUNT)) {
			stmt.setString(1, game);
			try (ResultSet rs = stmt.executeQuery()) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
