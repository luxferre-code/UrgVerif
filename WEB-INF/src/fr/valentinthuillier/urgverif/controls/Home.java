package fr.valentinthuillier.urgverif.controls;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if(session.getAttribute("matosIndspo") != null) {
            session.removeAttribute("matosIndspo");
        }
        System.out.println("Home Servlet active");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/index.jsp");
        rd.forward(req, resp);
    }

}