package fr.valentinthuillier.urgverif.servlets;

import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.Password;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dao.AgentDAO;
import fr.valentinthuillier.urgverif.model.dao.CentreDAO;
import fr.valentinthuillier.urgverif.model.dto.Agent;
import fr.valentinthuillier.urgverif.model.dto.Centre;
import fr.valentinthuillier.urgverif.model.dto.Gallon;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/install")
public class InstallServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rq;
        if(!DS.isConfigured()) {
            rq = req.getRequestDispatcher("/WEB-INF/views/install.jsp");
        } else {
            rq = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        }
        rq.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hostIP = req.getParameter("hostip");
        String port = req.getParameter("port");
        String dbName = req.getParameter("dbname");
        String dbUser = req.getParameter("dbuser");
        String dbPassword = req.getParameter("dbpassword");

        String center = req.getParameter("center");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");

        String adminNom = req.getParameter("adminnom");
        String adminPrenom = req.getParameter("adminprenom");
        String adminMatricule = req.getParameter("adminmatricule");
        String adminEmail = req.getParameter("adminemail");
        String adminPassword = req.getParameter("adminpassword");
        String adminConfirmPassword = req.getParameter("adminconfirmpassword");

        if(hostIP == null || hostIP.isBlank() || port == null || port.isBlank() || dbName == null || dbName.isBlank() || dbUser == null || dbUser.isBlank() || dbPassword == null || dbPassword.isBlank() || center == null || center.isBlank() || address == null || address.isBlank() || phone == null || phone.isBlank() || adminNom == null || adminNom.isBlank() || adminPrenom == null || adminPrenom.isBlank() || adminMatricule == null || adminMatricule.isBlank() || adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank() || adminConfirmPassword == null || adminConfirmPassword.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        if(!adminPassword.equals(adminConfirmPassword)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Passwords do not match");
            return;
        }

        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        hostIP = cst.translate(hostIP);
        port = cst.translate(port);
        dbName = cst.translate(dbName);
        dbUser = cst.translate(dbUser);
        dbPassword = cst.translate(dbPassword);

        center = cst.translate(center);
        address = cst.translate(address);
        phone = cst.translate(phone);

        adminNom = cst.translate(adminNom);
        adminPrenom = cst.translate(adminPrenom);
        adminMatricule = cst.translate(adminMatricule);
        adminEmail = cst.translate(adminEmail);
        adminPassword = Password.hash(adminPassword); 

        try {
            DS.configure("org.postgresql.Driver", "jdbc:postgresql://" + hostIP + ":" + port + "/" + dbName, dbUser, dbPassword);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while configuring the database");
            return;
        }

        CentreDAO centerDAO = new CentreDAO();
        Centre c = centerDAO.save(new Centre(center, address, phone));
        if(c == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while saving the center");
            return;
        }

        Agent admin = new Agent(adminMatricule, adminNom, adminPrenom, adminPassword, adminEmail, c.getID(), Gallon.ADMINISTRATEUR);
        AgentDAO agentDAO = new AgentDAO();
        admin = agentDAO.save(admin);
        if(admin == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while saving the admin");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }

}
