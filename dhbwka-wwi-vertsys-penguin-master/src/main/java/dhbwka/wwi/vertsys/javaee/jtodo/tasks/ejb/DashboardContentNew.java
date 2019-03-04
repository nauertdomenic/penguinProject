/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.jtodo.dashboard.ejb.DashboardTile;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Species;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Dashboard-Kacheln für Aufgaben.
 */
@Stateless(name = "tasks")
public class DashboardContentNew implements DashboardContentProvider {

    @EJB
    private SpeciesBean speciesBean;
    
    @EJB
    private AnimalBean animalBean;

       /**
     * Vom Dashboard aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Aufgaben
        // in allen Kategorien erzeugen
        DashboardSection section = this.createSection(null);
        sections.add(section);

        // Anschließend je Kategorie einen weiteren Abschnitt erzeugen
        List<Species> allSpecies = this.speciesBean.findAllSorted();

        for (Species species : allSpecies) {
            section = this.createSection(species);
            sections.add(section);
        }
    }
    
    /**
     * Hilfsmethode, die für die übergebene Aufgaben-Kategorie eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Je Aufgabenstatus wird eine Kachel
     * erzeugt. Zusätzlich eine Kachel für alle Aufgaben innerhalb der
     * jeweiligen Kategorie.
     *
     * Ist die Kategorie null, bedeutet dass, dass eine Rubrik für alle Aufgaben
     * aus allen Kategorien erzeugt werden soll.
     *
     * @param species Aufgaben-Kategorie, für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection createSection(Species species) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        if (species != null) {
            section.setLabel(species.getName());
        } else {
            section.setLabel("Alle Species");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(species, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);
        
        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }
    
    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param species
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(Species species, String label, String cssClass, String icon) {
        int amount = animalBean.search(null, species).size();
        String href = "/app/animals/list/";

        if (species != null) {
            href = WebUtils.addQueryParameter(href, "search_species", "" + species.getId());
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
