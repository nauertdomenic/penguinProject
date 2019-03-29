package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.tierarten.ejb.TierartBean;
import projectanimal.tierarten.jpa.Tierart;

/**
 *
 * @author phoenix
 *
 * Rest für alle Tierarten die $name sind
 */
@Stateless
@Path("tierartRest/{name}")
public class TierartnameRest {

    @EJB
    private TierartBean tierartBean;

    @GET
    public String doGet(@PathParam("name") String name) {
        // Anzuzeigende Tierarten suchen
        List<Tierart> tierarten = this.tierartBean.findAllTierartByTierartname(name);
        Gson gson = new Gson();
        String json = gson.toJson(tierarten);
        return json;
    }
}
