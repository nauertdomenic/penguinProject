package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.TierartBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Tierart;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Tierarten.
 */
@WebServlet(urlPatterns = {"/app/tierarten/list/"})
public class TaskListServlet extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private TierartBean taskBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
       
        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
       
        // Anzuzeigende Aufgaben suchen
        Category category = null;
       
        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        List<Tierart> tierarten = this.taskBean.search(searchText, category);
        request.setAttribute("tierarten", tierarten);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/tierart_list.jsp").forward(request, response);
    }
}
