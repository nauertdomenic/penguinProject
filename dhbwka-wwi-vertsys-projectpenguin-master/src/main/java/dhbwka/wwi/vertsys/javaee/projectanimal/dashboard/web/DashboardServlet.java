package dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.projectanimal.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite mit dem Übersichts-Dashboard.
 */
@WebServlet(urlPatterns = {"/app/dashboard/"})
public class DashboardServlet extends HttpServlet {

    // Kacheln für Tierarten
    @EJB(beanName = "tierarten")
    DashboardContentProvider tierartContent;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dashboard-Rubriken und Kacheln erzeugen und im Request Context ablegen
        List<DashboardSection> sections = new ArrayList<>();
        request.setAttribute("sections", sections);
        
        tierartContent.createDashboardContent(sections);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp").forward(request, response);
    }

}
