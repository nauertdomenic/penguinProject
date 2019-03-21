package projectanimal.whatever.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.jpa.Spezies;

/**
 *
 * @author simon
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
