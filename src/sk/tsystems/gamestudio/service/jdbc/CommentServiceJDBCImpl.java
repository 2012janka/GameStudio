package sk.tsystems.gamestudio.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

import sk.tsystems.gamestudio.service.CommentService;

public class CommentServiceJDBCImpl implements CommentService {

	public static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	public static final String USER = "gamestudio";
	public static final String PASSWORD = "gamestudio";

	private Connection con;
	private GamePlayerService id = new GamePlayerService();

	// public static final String INSERT_QUERY = "INSERT INTO comments VALUES
	// (25, ?, ?, ?)";
	public static final String INSERT_QUERY = "INSERT INTO comments VALUES (indexes.nextval, ?, ?, ?)";

	public static String SELECT_QUERY = "SELECT c.playerComment, p.name FROM COMMENTS c join GAME g on c.GAME_ID = g.GAME_ID join PLAYERS p on p.player_ID=c.player_ID where g.NAME=?";

	public CommentServiceJDBCImpl() {
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
	public void add(Comment comment) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(INSERT_QUERY)) {
			stmt.setInt(1, id.findGameID(comment.getGame()));
			stmt.setInt(2, id.findPlayerID(comment.getPlayer()));
			stmt.setString(3, comment.getComment());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
	}

	@Override
	public List<Comment> findCommentForGame(String game) {
		checkconn();

		try (PreparedStatement stmt = con.prepareStatement(SELECT_QUERY)) {
			stmt.setString(1, game);
			try (ResultSet rs = stmt.executeQuery()) {
				List<Comment> comments = new ArrayList<>();
				while (rs.next()) {
					comments.add(new Comment(rs.getString(2), game, rs.getString(1)));
				}
				System.out.println(commentsToString(comments));
				return comments;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String commentsToString(List<Comment> comments) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Comment c : comments) {
			sb.append((i + 1) + ". ");
			sb.append(c).toString();
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}
}
