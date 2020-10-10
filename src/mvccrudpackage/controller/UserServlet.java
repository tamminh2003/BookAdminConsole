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
import javax.servlet.http.HttpSession;

import mvccrudpackage.model.bean.Book;
import mvccrudpackage.model.dao.UserDAO;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// TODO Remove comment once UserDAO class is done
	private UserDAO userDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }
    
    // TODO Remove comment once UserDAO class is done
	public void init() {
		userDAO = new UserDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("action = " + action);
		if(action == null) action = "no action"; // Avoid error at action.equals()
		
		try {
			HttpSession session = request.getSession(false);
		
			if(action.equals("userLogin"))
				userDAO.userLogin(session.getId());
			else if(action.equals("userLogout"))
				userDAO.userLogout(session.getId());
				
			
			// -------------------------------------
			if (userDAO.userCheck(session.getId())) {
				// ---------------------------------
				switch (action) {
				case "search":
					searchBook(request, response);
					break;
				case "select":
					selectBook(request, response);
					break;
				default:
					listBook(request, response);
					break;
			}
				// ---------------------------------
			} else {
				response.sendRedirect("Login.jsp");
			}
			// -------------------------------------
			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void searchBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String category = request.getParameter("search");
		List<Book> listBook = userDAO.selectCategory(category);
		request.setAttribute("listBook", listBook);
		request.setAttribute("search", category);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}

	private void selectBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = userDAO.selectBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("book.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);
	}
	

	private void listBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		List<Book> listBook = userDAO.selectAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");
		dispatcher.forward(request, response);
	}

}
