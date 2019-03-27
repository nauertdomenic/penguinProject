package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import projectanimal.tierarten.ejb.TierartBean;
import projectanimal.tierarten.jpa.Tierart;

/**
 *
 * @author phoenix
 *
 * Rest für alle Tierarten
 */
@Stateless
@Path("tierartliste")
public class TierartListRest {

    @EJB
    private TierartBean tierartBean;

    @GET
    public String doGet() {
        // Anzuzeigende Tierarten suchen
        List<Tierart> tierarten = this.tierartBean.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(tierarten);
        return json;
    }
}