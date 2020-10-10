package mvccrudpackage.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDAO {
	
	/* Database URL, Username and Password */
	private String DBURL = "jdbc:mysql://localhost:3306/bookstore";
	private String DBUsername = "root";
	private String DBPassword = "mysql";
	
	/* Database userLogin and userLogout operation SQL */
	private String SESSIONUPDATE = "INSERT INTO session VALUES (?, false);";
	private String SESSIONDELETE = "DELETE FROM session WHERE sessionid = ?;";
	private String USERCHECK = "select * from session where sessionid = ?;";


	/* Constructor */
	public UserDAO() {

	}
	
	/* SQL Connection Methods */
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void userLogin(String userSessionID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SESSIONUPDATE);
			preparedStatement.setString(1, userSessionID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
			// Step 4: Process the ResultSet object.
	
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
	}
	
	/* User Login and Logout method */
	public void userLogout(String userSessionID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SESSIONDELETE);
			preparedStatement.setString(1, userSessionID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
			// Step 4: Process the ResultSet object.
	
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
	}
	
	public boolean userCheck(String sessionID) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(USERCHECK);
			preparedStatement.setString(1, sessionID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			result = rs.next();
			
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return result;
		
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	private void finallySQLException(Connection c, PreparedStatement p, ResultSet r) {
		if (r != null) {
			try {
				r.close();
			} catch (Exception e) {
			}
			r = null;
		}
		if (p != null) {
			try {
				p.close();
			} catch (Exception e) {
			}
			p = null;
		}
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				c = null;
			}
		}
	}
	
}
