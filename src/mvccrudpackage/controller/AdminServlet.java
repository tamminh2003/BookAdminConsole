package mvccrudpackage.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvccrudpackage.model.bean.Book;
import mvccrudpackage.model.bean.Category;
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
	}

	public void init() {
		adminDAO = new AdminDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String username = request.getParameter("username");
		System.out.println("action = " + action);

		if (action == null)
			action = "no action";

		try {
			HttpSession session = request.getSession(false);

			if (action.equals("adminLogin")) {
				adminDAO.adminLogin(session.getId(), username);
			} else if (action.equals("adminLogout")) {
				adminDAO.adminLogout(session.getId());
			}

			// -------------------------------------
			if (adminDAO.adminCheck(session.getId())) {
				// ---------------------------------
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
				case "select":
					selectBook(request, response);
					break;
				case "category":
					showCategory(request, response);
					break;
				case "addCategory":
					addCategory(request, response);
					break;
				case "deleteCategory":
					deleteCategory(request, response);
					break;
				case "showEditCategory":
					showEditCategory(request, response);
					break;
				case "editCategory":
					editCategory(request, response);
					break;
				default:
					listBook(request, response);
					break;
				}
				// ---------------------------------
			} else {
				response.sendRedirect("adminLogin.jsp");
			}
			// -------------------------------------

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}// End of doPost method

	private void editCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Category category = new Category();
		category.setCid(Integer.parseInt(request.getParameter("cid")));
		category.setCategoryTitle(request.getParameter("categorytitle"));

		adminDAO.editCategory(category);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=category&login=1");
	}

	private void showEditCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Category existingCategory = adminDAO.selectCategory(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("newCategory.jsp");
		request.setAttribute("category", existingCategory);
		dispatcher.forward(request, response);
	}

	private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		adminDAO.deleteCategory(id);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=category&login=1");
	}

	private void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		Category category = new Category();
		category.setCategoryTitle("categoryTitle");
		adminDAO.insertCategory(category);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=category&login=1");
	}

	private void showCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Category> listCategory = adminDAO.selectAllCategory();
		request.setAttribute("listCategory", listCategory);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminCategory.jsp");
		dispatcher.forward(request, response);
	}

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

	private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Book book = new Book();

		Timestamp date = Timestamp.valueOf(request.getParameter("publisheddate") + " 0:0:0");

		System.out.println("Date from jsp: " + request.getParameter("publisheddate"));

		book.setCid(Integer.parseInt(request.getParameter("cid")));
		book.setBooktitle(request.getParameter("booktitle"));
		book.setDescription(request.getParameter("description"));
		book.setAuthor(request.getParameter("author"));
		book.setPublisheddate(date);
		book.setIsbn(request.getParameter("isbn"));
		book.setPrice(Double.parseDouble(request.getParameter("price")));
		book.setNoofpages(Integer.parseInt(request.getParameter("noofpages")));

		adminDAO.insertBook(book);
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

	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Book book = new Book();
		Timestamp date = Timestamp.valueOf(request.getParameter("publisheddate") + " 0:0:0");

		book.setBid(Integer.parseInt(request.getParameter("bid")));
		book.setCid(Integer.parseInt(request.getParameter("cid")));
		book.setBooktitle(request.getParameter("booktitle"));
		book.setDescription(request.getParameter("description"));
		book.setAuthor(request.getParameter("author"));
		book.setPublisheddate(date);
		book.setIsbn(request.getParameter("isbn"));
		book.setPrice(Double.parseDouble(request.getParameter("price")));
		book.setNoofpages(Integer.parseInt(request.getParameter("noofpages")));

		adminDAO.updateBook(book);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=list&login=1");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		adminDAO.deleteBook(id);
		response.sendRedirect(request.getContextPath() + "/AdminServlet?action=list&login=1");
	}

	private void searchBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String category = request.getParameter("search");
		List<Book> listBook = adminDAO.selectCategory(category);
		request.setAttribute("listBook", listBook);
		request.setAttribute("search", category);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}

	private void selectBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = adminDAO.selectBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("book.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);
	}
}
