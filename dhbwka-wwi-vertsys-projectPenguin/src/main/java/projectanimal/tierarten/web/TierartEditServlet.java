package projectanimal.tierarten.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import projectanimal.common.ejb.UserBean;
import projectanimal.common.ejb.ValidationBean;
import projectanimal.common.web.FormValues;
import projectanimal.common.web.WebUtils;
import projectanimal.tierarten.ejb.SpeziesBean;
import projectanimal.tierarten.ejb.TierartBean;
import projectanimal.tierarten.jpa.Tierart;
import projectanimal.tierarten.jpa.TierartStatus;

/**
 *
 * @author phoenix
 *
 * Servlet für anlegen, bearbeiten und löschen von Tierarten
 */
@WebServlet(urlPatterns = "/app/tierarten/tierart/*")
public class TierartEditServlet extends HttpServlet {

    @EJB
    TierartBean tierartBean;

    @EJB
    SpeziesBean speziesBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Spezies und Stati für die Suchfelder ermitteln
        request.setAttribute("spezien", this.speziesBean.findAllSorted());
        request.setAttribute("statuses", TierartStatus.values());

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

        String tierartSpezies = request.getParameter("tierart_spezies");
        String tierartTierartname = request.getParameter("tierart_tierartname");
        String tierartStatus = request.getParameter("tierart_status");
        String tierartGewicht = request.getParameter("tierart_gewicht");
        String tierartLebensdauer = request.getParameter("tierart_lebensdauer");
        String tierartSchmeckt = request.getParameter("tierart_schmeckt");

        // Error handling
        Tierart tierart = this.getRequestedTierart(request);

        if (tierartSpezies != null && !tierartSpezies.trim().isEmpty()) {
            try {
                tierart.setSpezies(this.speziesBean.findById(Long.parseLong(tierartSpezies)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        try {
            tierart.setStatus(TierartStatus.valueOf(tierartStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        if (!tierartGewicht.isBlank()) {
            try {
                tierart.setGewicht(Double.valueOf(tierartGewicht));
            } catch (NumberFormatException nfe) {
                errors.add("Bitte gebe eine Zahl für das Gewicht ein.");
            }
        }

        if (!tierartLebensdauer.isBlank()) {
            try {
                tierart.setLebensdauer(Double.valueOf(tierartLebensdauer));
            } catch (NumberFormatException nfe) {
                errors.add("Bitte gebe eine Zahl für die Lebensdauer ein.");
            }
        }

        tierart.setSchmeckt(Boolean.valueOf(tierartSchmeckt));

        tierart.setDate(new Date(System.currentTimeMillis()));
        tierart.setTime(new Time(System.currentTimeMillis()));

        tierart.setTierartname(tierartTierartname);

        this.validationBean.validate(tierart, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.tierartBean.update(tierart);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/tierarten/list/"));
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
        this.tierartBean.delete(tierart);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/tierarten/list/"));
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
        tierart.setDate(new Date(System.currentTimeMillis()));
        tierart.setTime(new Time(System.currentTimeMillis()));

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
            tierart = this.tierartBean.findById(Long.parseLong(tierartId));
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

        if (tierart.getSpezies() != null) {
            values.put("tierart_spezies", new String[]{
                "" + tierart.getSpezies().getId()
            });
        }

        values.put("tierart_status", new String[]{
            tierart.getStatus().toString()
        });

        values.put("tierart_tierartname", new String[]{
            tierart.getTierartname()
        });

        values.put("tierart_gewicht", new String[]{
            String.valueOf(tierart.getGewicht())
        });

        values.put("tierart_lebensdauer", new String[]{
            String.valueOf(tierart.getLebensdauer())
        });

        values.put("tierart_date", new String[]{
            WebUtils.formatDate(tierart.getDate())
        });

        values.put("tierart_time", new String[]{
            WebUtils.formatTime(tierart.getTime())
        });

        System.out.println("TESTTESTTEAKSDKLFSLKF");
        System.out.println(tierart.getSchmecktString());

        values.put("tierart_schmeckt", new String[]{
            tierart.getSchmecktString()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
