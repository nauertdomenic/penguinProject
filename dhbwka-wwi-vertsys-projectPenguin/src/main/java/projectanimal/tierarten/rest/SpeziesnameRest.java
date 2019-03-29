package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.tierarten.ejb.SpeziesBean;
import projectanimal.tierarten.jpa.Spezies;

/**
 *
 * @author phoenix
 *
 * Rest für alle Spezies die $name sind
 */
@Stateless
@Path("speziesname/{name}")
public class SpeziesnameRest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String doGet(@PathParam("name") String name) {
        // Anzuzeigende Spezies suchen
        List<Spezies> spezies = this.speziesBean.findAllSpeziesByName(name);
        Gson gson = new Gson();
        String json = gson.toJson(spezies);
        return json;
    }
}
