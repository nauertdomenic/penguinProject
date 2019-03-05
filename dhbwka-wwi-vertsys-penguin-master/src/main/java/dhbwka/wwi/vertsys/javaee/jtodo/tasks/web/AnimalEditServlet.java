/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.tasks.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.AnimalBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.SpeciesBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Animal;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
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
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/animals/animal/*")
public class AnimalEditServlet extends HttpServlet {

    @EJB
    AnimalBean animalBean;
    
    @EJB
    SpeciesBean speciesBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean walliBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("allSpecies", this.speciesBean.findAllSorted());
        request.setAttribute("statuses", TaskStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Animal animal = this.getRequestedAnimal(request);
        request.setAttribute("edit", animal.getId() != 0);
                                
        if (session.getAttribute("animal_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("animal_form", this.createAnimalForm(animal));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/animal_edit.jsp").forward(request, response);
        
        session.removeAttribute("animal_form");
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
                this.saveAnimal(request, response);
                break;
            case "delete":
                this.deleteAnimal(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveAnimal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String animalSpecies = request.getParameter("animal_species");
        String animalDueDate = request.getParameter("animal_due_date");
        String animalDueTime = request.getParameter("animal_due_time");
        String animalName = request.getParameter("animal_name");

        Animal animal = this.getRequestedAnimal(request);

        if (animalSpecies != null && !animalSpecies.trim().isEmpty()) {
            try {
                animal.setSpecies(this.speciesBean.findById(Long.parseLong(animalSpecies)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(animalDueDate);
        Time dueTime = WebUtils.parseTime(animalDueTime);

        if (dueDate != null) {
            animal.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            animal.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        animal.setName(animalName);

        this.walliBean.validate(animal, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.animalBean.update(animal);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/animals/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("animal_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteAnimal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Animal animal = this.getRequestedAnimal(request);
        this.animalBean.delete(animal);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/animals/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Animal getRequestedAnimal(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Animal animal = new Animal();
        animal.setOwner(this.userBean.getCurrentUser());
        animal.setDueDate(new Date(System.currentTimeMillis()));
        animal.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String animalId = request.getPathInfo();

        if (animalId == null) {
            animalId = "";
        }

        animalId = animalId.substring(1);

        if (animalId.endsWith("/")) {
            animalId = animalId.substring(0, animalId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            animal = this.animalBean.findById(Long.parseLong(animalId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return animal;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createAnimalForm(Animal animal) {
        Map<String, String[]> values = new HashMap<>();

        values.put("animal_owner", new String[]{
            animal.getOwner().getUsername()
        });

        if (animal.getSpecies() != null) {
            values.put("task_species", new String[]{
                "" + animal.getSpecies().getId()
            });
        }

        values.put("animal_due_date", new String[]{
            WebUtils.formatDate(animal.getDueDate())
        });

        values.put("animal_due_time", new String[]{
            WebUtils.formatTime(animal.getDueTime())
        });

        values.put("animal_name", new String[]{
            animal.getName()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
