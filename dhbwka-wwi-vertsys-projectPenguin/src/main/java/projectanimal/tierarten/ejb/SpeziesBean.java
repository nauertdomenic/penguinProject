package projectanimal.tierarten.ejb;

import projectanimal.common.ejb.EntityBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
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
}
