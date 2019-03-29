package projectanimal.tierarten.ejb;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import projectanimal.common.ejb.EntityBean;
import projectanimal.tierarten.jpa.Spezies;

/**
 *
 * @author phoenix
 *
 * EJB für die Spezies
 */
@Stateless
@RolesAllowed("app-user")
public class SpeziesBean extends EntityBean<Spezies, Long> {

    public SpeziesBean() {
        super(Spezies.class);
    }

    /**
     * Auslesen aller Spezies, alphabetisch sortiert.
     *
     * @return Liste mit allen Spezies
     */
    public List<Spezies> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Spezies c ORDER BY c.name").getResultList();
    }

    /**
     * Auslesen aller Datensätze (Reihenfolge undefiniert) mit Attribut name
     *
     * @param name
     * @return Liste mit allen Datensätzen
     */
    public List<Spezies> findAllSpeziesByName(String name) {
        return em.createQuery("SELECT t FROM Spezies t WHERE t.name = :name")
                .setParameter("name", name)
                .getResultList();
    }
}
