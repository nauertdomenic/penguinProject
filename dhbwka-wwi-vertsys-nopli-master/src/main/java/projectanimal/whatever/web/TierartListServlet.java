package projectanimal.whatever.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.ejb.TierartBean;
import projectanimal.whatever.jpa.Spezies;
import projectanimal.whatever.jpa.Tierart;
import projectanimal.whatever.jpa.TierartStatus;

/**
 *
 * @author simon
 */
@WebServlet(urlPatterns = {"/app/tierarten/list/"})
public class TierartListServlet extends HttpServlet {

    @EJB
    private SpeziesBean speziesBean;

    @EJB
    private TierartBean tierartBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("spezien", this.speziesBean.findAllSorted());
        request.setAttribute("statuses", TierartStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchSpezies = request.getParameter("search_spezies");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Spezies spezies = null;
        TierartStatus status = null;

        if (searchSpezies != null) {
            try {
                spezies = this.speziesBean.findById(Long.parseLong(searchSpezies));
            } catch (NumberFormatException ex) {
                spezies = null;
            }
        }
        
        if (searchStatus != null) {
            try {
                status = TierartStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Tierart> tierarten = this.tierartBean.search(searchText, spezies, status);
        request.setAttribute("tierarten", tierarten);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/tierart_list.jsp").forward(request, response);
    }
}
