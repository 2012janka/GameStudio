package sk.tsystems.gamestudio.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GamePlayerService {
	public static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	public static final String USER = "gamestudio";
	public static final String PASSWORD = "gamestudio";

	private Connection con;

	public static String SELECT_GAME_ID_QUERY = "SELECT GAME_ID FROM GAME where NAME=?";
	public static String SELECT_PLAYER_ID_QUERY = "SELECT PLAYER_ID FROM PLAYERS where NAME=?";

	public GamePlayerService() {
		try {
			// Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int findGameID(String gameName) {
		try (PreparedStatement stmt = con.prepareStatement(SELECT_GAME_ID_QUERY);) {
			stmt.setString(1, gameName);
			try(ResultSet rs = stmt.executeQuery())
			{
				if(rs.next())
				{
					int id = rs.getInt(1);
					return id;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int findPlayerID(String playerName) {
		try (PreparedStatement stmt = con.prepareStatement(SELECT_PLAYER_ID_QUERY))
				{
				stmt.setString(1, playerName);

				try(ResultSet rs = stmt.executeQuery())
				{
					if(rs.next())
					{
						int id = rs.getInt(1);
						return id;
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
