package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Tierart;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("app-user")
public class TierartBean extends EntityBean<Tierart, Long> { 
   
    public TierartBean() {
        super(Tierart.class);
    }
    
    /**
     * Alle Tierarten eines Benutzers zurückliefern.
     * @param username Benutzername
     * @return Alle Tierarten des Benutzers
     */
    public List<Tierart> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Tierart t WHERE t.owner.username = :username")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    /**
     * Suche nach Tierarten anhand ihrer Bezeichnung, Spezies.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Spezies (optional)
     * @return Liste mit den gefundenen Tierarten
     */
    public List<Tierart> search(String search, Category category) {
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
        
        // WHERE t.category = :category
        if (category != null) {
            p = cb.and(p, cb.equal(from.get("category"), category));
            query.where(p);
        }
                      
        return em.createQuery(query).getResultList();
    }
}
