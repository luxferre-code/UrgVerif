package fr.valentinthuillier.urgverif.servlets;

import java.io.IOException;

import fr.valentinthuillier.urgverif.Password;
import fr.valentinthuillier.urgverif.model.dao.AgentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServelt extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("matricule") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        RequestDispatcher rq = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        rq.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession().getAttribute("matricule") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        String matricule = req.getParameter("matricule");
        String password = req.getParameter("password");

        if(matricule == null || matricule.isBlank() || password == null || password.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        AgentDAO dao = new AgentDAO();
        if(dao.check(matricule, password)) {
            req.getSession().setAttribute("matricule", matricule);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login failed");
        }
    }

}
