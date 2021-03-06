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
import mvccrudpackage.model.bean.Category;

public class AdminDAO {

	/* Database URL, Username and Password */
	private String DBURL = "jdbc:mysql://localhost:3306/bookstore";
	private String DBUsername = "root";
	private String DBPassword = "mysql";

	
	/* Database operation SQL */
	private String INSERTBOOKSQL = "insert into books values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String SELECTBOOKID = "select * from books left join book_category using (cid) where bid =?;";
	private String DELETEBOOKSQL = "delete from Books where bid = ?;";
	private String UPDATEBOOKSQL = "update Books set cid=?, booktitle=?, description=?, author=?, "
			+ "publisheddate=?, isbn=?, price=?, noofpages=? where bid = ?;";
	private String SELECTMAXBID = "select max(bid) as bid from bookstore.books;";
	private String SELECTCATEGORY = "select * from books left join book_category using (cid) "
			+ "where cid = (select cid from book_category where categorytitle = ?);";
	private String SELECTALLBOOKS = "select * from books left join book_category using (cid) order by bid;";
	private String SESSIONUPDATE = "INSERT INTO session VALUES (?, ?)";
	private String SESSIONDELETE = "DELETE FROM session WHERE sessionid = ?;";
	private String ADMINCHECK = "select isAdmin from users where username = (select username from session where sessionid= ?)";
	private String SELECTALLCATEGORY = "SELECT * FROM book_category;";
	private String INSERTCATEGORYSQL = "INSERT INTO book_category VALUES (?, ?);";
	private String SELECTMAXCID = "SELECT MAX(cid) AS CID FROM book_category;";
	private String DELETECATEGORYSQL = "DELETE FROM book_category WHERE cid = ?;";
	private String SELECTCATEGORYID = "SELECT * FROM book_category WHERE cid = ?;";
	private String EDITCATEGORYSQL = "UPDATE book_category SET categorytitle = ? WHERE cid = ?;";
	private String CATEGORYCOUNTSQL = "SELECT COUNT(*) AS count FROM book_category;";
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
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/* Admin Operation methods */
	public void insertBook(Book book) throws SQLException {
		int maxBid = selectMaxBid();
		
		System.out.println(INSERTBOOKSQL);
		Connection connection = null;
		PreparedStatement preparedStatement = null;

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

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
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

			preparedStatement.setInt(1, book.getCid());
			preparedStatement.setString(2, book.getBooktitle());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setString(4, book.getAuthor());
			preparedStatement.setTimestamp(5, book.getPublisheddate());
			preparedStatement.setString(6, book.getIsbn());
			preparedStatement.setDouble(7, book.getPrice());
			preparedStatement.setInt(8, book.getNoofpages());
			preparedStatement.setInt(9, book.getBid());
			
			System.out.println(preparedStatement);
			
			bookUpdated = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		
		return bookUpdated;
	}
	
	public void adminLogin(String adminSessionID, String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SESSIONUPDATE);
			preparedStatement.setString(1, adminSessionID);
			preparedStatement.setString(2, username);
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
	
	public void adminLogout(String adminSessionID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SESSIONDELETE);
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
	
	public List<Category> selectAllCategory() {
		List<Category> listCategory = new ArrayList<Category>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTALLCATEGORY);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				Category category = new Category();

				category.setCid(rs.getInt("cid"));
				category.setCategoryTitle(rs.getString("categorytitle"));

				listCategory.add(category);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		
		return listCategory;
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
	
	public void insertCategory(Category category) throws SQLException {
		int maxCid = selectMaxCid();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(INSERTCATEGORYSQL);
			
			preparedStatement.setInt(1, maxCid + 1);
			preparedStatement.setString(2, category.getCategoryTitle());
			
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
	}
	
	public boolean deleteCategory(int id) throws SQLException {
			boolean categoryDeleted = false;
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				connection = getConnection();
				preparedStatement = connection.prepareStatement(DELETECATEGORYSQL);
				preparedStatement.setInt(1, id);
				categoryDeleted = preparedStatement.executeUpdate() > 0 ? true : false;
			} finally {
				finallySQLException(connection, preparedStatement, null);
			}
			return categoryDeleted;
	}
	
	public Category selectCategory(int id) {
		Category category = new Category();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTCATEGORYID);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				category.setCid(rs.getInt("cid"));
				category.setCategoryTitle(rs.getString("categorytitle"));	
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);

		}
		return category;
	}
	
	public boolean editCategory(Category category) throws SQLException {
		boolean categoryUpdated = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(EDITCATEGORYSQL);

			preparedStatement.setString(1, category.getCategoryTitle());
			preparedStatement.setInt(2, category.getCid());
			
			System.out.println(preparedStatement);
			
			categoryUpdated = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		
		return categoryUpdated;
	}
	
	public int categoryCount() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(CATEGORYCOUNTSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			
		} finally {
			
		}
		return result;
	}

	private int selectMaxCid() {
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTMAXCID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				result = rs.getInt("cid");
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);

		}
		return result;
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
