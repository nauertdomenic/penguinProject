package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Spezies.
 */
@Stateless
@RolesAllowed("app-user")
public class CategoryBean extends EntityBean<Category, Long> {

    public CategoryBean() {
        super(Category.class);
    }

    /**
     * Auslesen aller Spezies, alphabetisch sortiert.
     *
     * @return Liste mit allen Spezies
     */
    public List<Category> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Category c ORDER BY c.name").getResultList();
    }
}
