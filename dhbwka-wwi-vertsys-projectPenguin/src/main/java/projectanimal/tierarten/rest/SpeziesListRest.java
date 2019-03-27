package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import projectanimal.tierarten.ejb.SpeziesBean;
import projectanimal.tierarten.jpa.Spezies;

/**
 *
 * @author phoenix
 *
 * Rest für alle Spezies
 */
@Stateless
@Path("speziesliste")
public class SpeziesListRest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String doGet() {
        // Anzuzeigende Spezies suchen
        List<Spezies> spezies = this.speziesBean.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(spezies);
        return json;
    }
}