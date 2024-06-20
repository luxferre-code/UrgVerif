package fr.valentinthuillier.urgverif.controls;

import java.io.IOException;

import fr.valentinthuillier.urgverif.model.DS;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/install")
public class InstallServelt extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!DS.isConfigured()) {
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/install.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!DS.isConfigured()) {
            String driver = req.getParameter("driver");
            String url = req.getParameter("url");
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            try {
                DS.configure(driver, url, login, password);
                resp.sendRedirect(req.getContextPath() + "/");
            } catch(Exception e) {
                req.setAttribute("error", e.getMessage());
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/install.jsp");
                rd.forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
