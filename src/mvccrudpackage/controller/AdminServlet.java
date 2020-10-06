package mvccrudpackage.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvccrudpackage.model.bean.Book;
import mvccrudpackage.model.dao.AdminDAO;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDAO adminDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		
		adminDAO = new AdminDAO();
		
	}
	
	protected static boolean checkAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		return(request.getParameter("admin").equals("1"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// String action = request.getServletPath();

		String login = request.getParameter("login");
		String action = request.getParameter("action");
		try {
			if (login != null) {

				if (action == null) {
					action = "No action";
				}

				switch (action) {
				
				case "new":
					showNewBook(request, response);
					break;
				case "insert":
					insertBook(request, response);
					break;
				case "delete":
					deleteBook(request, response);
					break;
				case "edit":
					showEditBook(request, response);
					break;
				case "update":
					updateBook(request, response);
					break;
				case "search":
					searchBook(request, response);
					break;
				default:
					listBook(request, response);
					break;
					
				}

			} else {
				response.sendRedirect("Login.jsp");
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}// End of doPost method

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Book> listBook = adminDAO.selectAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("formNewBook.jsp");
		dispatcher.forward(request, response);
	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Book e = new Book();
		
		String booktitle = request.getParameter("booktitle");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		//Book e = new Book(cid, booktitle, author, isbn, description);
		adminDAO.insertBook(e);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=list&login=1");
	}

	private void showEditBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = adminDAO.selectBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("formNewBook.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String booktitle = request.getParameter("booktitle");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		Book e = new Book(bid, cid, booktitle, author, isbn);
		adminDAO.updateBook(e);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=list&login=1");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		adminDAO.deleteBook(id);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=list&login=1");
	}
	
	private void searchBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String category = request.getParameter("search");
		List<Book> listBook = adminDAO.selectCategory(category);
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}
}
