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

import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.AnimalBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb.SpeciesBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Animal;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Species;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/animals/list/"})
public class AnimalListServlet extends HttpServlet {

    @EJB
    private SpeciesBean speciesBean;
    
    @EJB
    private AnimalBean animalBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("species", this.speciesBean.findAllSorted());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchSpecies = request.getParameter("search_species");

        // Anzuzeigende Aufgaben suchen
        Species species = null;

        if (searchSpecies != null) {
            try {
                species = this.speciesBean.findById(Long.parseLong(searchSpecies));
            } catch (NumberFormatException ex) {
                species = null;
            }
        }

        List<Animal> animals = this.animalBean.search(searchText, species);
        request.setAttribute("animals", animals);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/animals/animal_list.jsp").forward(request, response);
    }
}
