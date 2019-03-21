package projectanimal.whatever.ejb;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.EntityBean;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.*;
import javax.persistence.criteria.*;
import projectanimal.whatever.jpa.Spezies;
import projectanimal.whatever.jpa.Tierart;
import projectanimal.whatever.jpa.TierartStatus;

/**
 *
 * @author simon
 */
@Stateless
@RolesAllowed("app-user")
public class TierartBean extends EntityBean<Tierart, Long> {

    public TierartBean() {
        super(Tierart.class);
    }

    /**
     * Alle Tierarten eines Benutzers zurückliefern.
     *
     * @param username Benutzername
     * @return Alle Tierarten des Benutzers
     */
    public List<Tierart> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Tierart t WHERE t.owner.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * Suche nach Tierarten anhand ihrer Bezeichnung, Spezies.Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier mit
 der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     *
     *
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param spezies Spezies (optional)
     * @param status
     * @return Liste mit den gefundenen Tierarten
     */
    public List<Tierart> search(String search, Spezies spezies, TierartStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // SELECT t FROM Tierart t
        CriteriaQuery<Tierart> query = cb.createQuery(Tierart.class);
        Root<Tierart> from = query.from(Tierart.class);
        query.select(from);

        // WHERE t.tierartname LIKE :search
        Predicate p = cb.conjunction();

        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("tierartname"), "%" + search + "%"));
            query.where(p);
        }

        // WHERE t.spezies = :spezies
        if (spezies != null) {
            p = cb.and(p, cb.equal(from.get("spezies"), spezies));
            query.where(p);
        }
        
        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }

        return em.createQuery(query).getResultList();
    }
}
