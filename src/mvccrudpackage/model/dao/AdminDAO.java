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

import mvccrudpackage.model.bean.Book;

public class AdminDAO {

	/* Database URL, Username and Password */
	private String DBURL = "jdbc:mysql://localhost:3306/bookstore";
	private String DBUsername = "root";
	private String DBPassword = "mysql";

	
	/* Database operation */
	private String INSERTBOOKSQL = "insert into books values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private String SELECTBOOKID = "select * from books left join book_category using (cid) where bid =?;";
	private String DELETEBOOKSQL = "delete from Books where bid = ?;";
	private String UPDATEBOOKSQL = "update Books set booktitle = ?, author= ? where bid = ?;";
	private String SELECTMAXBID = "select max(bid) as bid from bookstore.books;";
	private String SELECTCATEGORY = "select * "
			+ "from books left join book_category using (cid)"
			+ " where cid = (select cid from book_category where categorytitle = ?);";

	private String SELECTALLBOOKS = "select * from books left join book_category using (cid) order by bid;";
	
	private String SESSIONUPDATE = "INSERT INTO session VALUES (?, true)";
	private String ADMINCHECK = "select isAdmin from session where sessionid = ?";
	/* Constructor */
	public AdminDAO() {

	}

	/* Methods */
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertBook(Book book) throws SQLException {
		int maxBid = selectMaxBid();
		
		System.out.println(INSERTBOOKSQL);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		// try-with-resource statement will auto close the connection.
		try {
			
			connection = getConnection();
			preparedStatement = connection.prepareStatement(INSERTBOOKSQL);
			preparedStatement.setInt(1, maxBid + 1);
			preparedStatement.setInt(2, book.getCid());
			preparedStatement.setString(3, book.getBooktitle());
			preparedStatement.setString(4, book.getDescription());
			preparedStatement.setString(5, book.getAuthor());
			preparedStatement.setTimestamp(6, book.getPublisheddate());
			preparedStatement.setString(7, book.getIsbn());
			preparedStatement.setDouble(8, book.getPrice());
			preparedStatement.setInt(9, book.getNoofpages());
			
			System.out.println(preparedStatement);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
	}

	public Book selectBook(int bid) {
		Book book = new Book();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTBOOKID);
			preparedStatement.setInt(1, bid);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				
				book.setBid(rs.getInt("bid"));
				book.setCid(rs.getInt("cid"));
				book.setBooktitle(rs.getString("booktitle"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setPublisheddate(rs.getTimestamp("publisheddate"));
				book.setIsbn(rs.getString("isbn"));
				book.setPrice(rs.getDouble("price"));
				book.setNoofpages(rs.getInt("noofpages"));
				book.setCategory(rs.getString("categorytitle"));
				
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);

		}
		return book;
	}

	public List<Book> selectAllBooks() {
		// Book book = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Book> books = new ArrayList<>();
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTALLBOOKS);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				Book book = new Book();
				
				book.setBid(rs.getInt("bid"));
				book.setBooktitle(rs.getString("booktitle"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setPublisheddate(rs.getTimestamp("publisheddate"));
				book.setIsbn(rs.getString("isbn"));
				book.setPrice(rs.getDouble("price"));
				book.setNoofpages(rs.getInt("noofpages"));
				book.setCategory(rs.getString("categorytitle"));

				books.add(book);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return books;
	}

	public boolean deleteBook(int id) throws SQLException {
		boolean bookDeleted = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(DELETEBOOKSQL);
			preparedStatement.setInt(1, id);
			bookDeleted = preparedStatement.executeUpdate() > 0 ? true : false;
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return bookDeleted;
	}

	public boolean updateBook(Book book) throws SQLException {
		boolean bookUpdated = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(UPDATEBOOKSQL);

			preparedStatement.setString(1, book.getBooktitle());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setInt(3, book.getBid());
			bookUpdated = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return bookUpdated;
	}
	
	public void adminLogin(String adminSessionID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SESSIONUPDATE);
			preparedStatement.setString(1, adminSessionID);
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
	
	public boolean adminCheck(String sessionID) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(ADMINCHECK);
			preparedStatement.setString(1, sessionID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			if(rs.next()) result = rs.getBoolean("isAdmin");
			
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return result;
		
	}
	
	public List<Book> selectCategory(String category) {
		List<Book> books = new ArrayList<>();
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTCATEGORY);
			System.out.println(preparedStatement);
			preparedStatement.setString(1, category);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				Book book = new Book();
				
				book.setBid(rs.getInt("bid"));
				book.setBooktitle(rs.getString("booktitle"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setPublisheddate(rs.getTimestamp("publisheddate"));
				book.setIsbn(rs.getString("isbn"));
				book.setPrice(rs.getDouble("price"));
				book.setNoofpages(rs.getInt("noofpages"));
				book.setCategory(rs.getString("categorytitle"));

				books.add(book);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		
		return books;
	}
	
	private int selectMaxBid() {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTMAXBID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				result = rs.getInt("bid");
			}
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
