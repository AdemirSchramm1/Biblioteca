package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	public static Connection getConexao() {
		String user = "root";
		String password = "Ademir010203@@";
		String url = "jdbc:mysql://localhost:3306/db_biblioteca";
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
