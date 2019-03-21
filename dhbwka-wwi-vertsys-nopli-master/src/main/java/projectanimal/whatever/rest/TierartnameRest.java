package projectanimal.whatever.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.whatever.ejb.TierartBean;
import projectanimal.whatever.jpa.Tierart;

/**
 *
 * @author simon
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
