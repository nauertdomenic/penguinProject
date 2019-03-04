/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.Species;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class SpeciesBean extends EntityBean<Species, Long> {

    public SpeciesBean() {
        super(Species.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Species
     */
    public List<Species> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Species c ORDER BY c.species").getResultList();
    }
}
