package mvccrudpackage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
				RequestDispatcher dispatcher = request.getRequestDispatcher("test.jsp");
				request.setAttribute("test", "Login successful");
				dispatcher.forward(request, response);
				// ---------------------------------
			} else {
				response.sendRedirect("Login.jsp");
			}
			// -------------------------------------
			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

}
