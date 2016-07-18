package sk.tsystems.gamestudio.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.CommentFromPlayer;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;

public class ScoreServiceJDBCImpl implements ScoreService {
	public static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	public static final String USER = "gamestudio";
	public static final String PASSWORD = "gamestudio";

	private Connection con;
	private GamePlayerService id = new GamePlayerService();

	public static final String INSERT_QUERY = "INSERT INTO score VALUES (indexes.nextval, ?, ?, ?)";
	public static String SELECT_QUERY = "SELECT s.score, p.name FROM SCORE s join GAME g on s.GAME_ID = g.GAME_ID join PLAYERS p on p.player_ID = s.player_ID where g.NAME=? order by s.score";

	public ScoreServiceJDBCImpl() {
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
	public void add(Score score) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, id.findGameID(score.getGame()));
			stmt.setInt(2, id.findPlayerID(score.getPlayer()));
			stmt.setInt(3, score.getScore());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Score> findTopScoreForGame(String game) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setString(1, game);
			try (ResultSet rs = stmt.executeQuery()) {
				List<Score> score = new ArrayList<>();
				int i = 0;
				while (rs.next() && i < 10) {
					score.add(new Score(rs.getString(2), game, rs.getInt(1)));
					i++;
				}
				//System.out.println(scoreToString(score));
				return score;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	
//	public String scoreToString(List<Score> score) {
//		StringBuilder sb = new StringBuilder();
//		int i = 0;
//		for (Score s : score) {
//			sb.append((i + 1) + ". ");
//			sb.append(s).toString();
//			sb.append("\n");
//			i++;
//		}
//		return sb.toString();
//	}

}
