package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.web;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb.TierartBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Tierart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Tierart.
 */
@WebServlet(urlPatterns = "/app/tasks/task/*")
public class TaskEditServlet extends HttpServlet {

    @EJB
    TierartBean taskBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        
        // Zu bearbeitende Tierart einlesen
        HttpSession session = request.getSession();

        Tierart tierart = this.getRequestedTierart(request);
        request.setAttribute("edit", tierart.getId() != 0);
                                
        if (session.getAttribute("tierart_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("tierart_form", this.createTierartForm(tierart));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/tierart_edit.jsp").forward(request, response);
        
        session.removeAttribute("tierart_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveTierart(request, response);
                break;
            case "delete":
                this.deleteTierart(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Tierart speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveTierart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String tierartCategory = request.getParameter("tierart_category");
        String tierartTierartname = request.getParameter("tierart_tierartname");
      
        Tierart tierart = this.getRequestedTierart(request);

        if (tierartCategory != null && !tierartCategory.trim().isEmpty()) {
            try {
                tierart.setCategory(this.categoryBean.findById(Long.parseLong(tierartCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        tierart.setTierartname(tierartTierartname);
       
        this.validationBean.validate(tierart, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.taskBean.update(tierart);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("tierart_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Tierart löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteTierart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Tierart tierart = this.getRequestedTierart(request);
        this.taskBean.delete(tierart);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
    }

    /**
     * Zu bearbeitende Tierart aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Tierart getRequestedTierart(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Tierart tierart = new Tierart();
        tierart.setOwner(this.userBean.getCurrentUser());
        
        // ID aus der URL herausschneiden
        String tierartId = request.getPathInfo();

        if (tierartId == null) {
            tierartId = "";
        }

        tierartId = tierartId.substring(1);

        if (tierartId.endsWith("/")) {
            tierartId = tierartId.substring(0, tierartId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            tierart = this.taskBean.findById(Long.parseLong(tierartId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return tierart;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param tierart Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-tierart
     */
    private FormValues createTierartForm(Tierart tierart) {
        Map<String, String[]> values = new HashMap<>();

        values.put("tierart_owner", new String[]{
            tierart.getOwner().getUsername()
        });

        if (tierart.getCategory() != null) {
            values.put("tierart_category", new String[]{
                "" + tierart.getCategory().getId()
            });
        }
      
        values.put("tierart_tierartname", new String[]{
            tierart.getTierartname()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
