package fr.valentinthuillier.urgverif.controls;

import fr.valentinthuillier.urgverif.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlertBox {

    private static final String FORMAT = "<script>alert('%s');</script>";
    
    public static void checkError(HttpServletRequest req, HttpServletResponse resp) {
        String erreur = (String) req.getAttribute("erreur");
        if(erreur == null || erreur.isBlank()) {
            return;
        }
        Log.info("AlertBox.checkError: Erreur = " + req.getAttribute("erreur"));
        String alert = String.format(FORMAT, erreur);
        try { resp.getWriter().write(alert); }
        catch(Exception e) { Log.error("AlertBox.checkError: Erreur lors de l'écriture de l'alerte: " + e.getMessage()); }
    }

}
