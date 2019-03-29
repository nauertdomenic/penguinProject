package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.common.ejb.UserBean;
import projectanimal.tierarten.ejb.TierartBean;
import projectanimal.tierarten.jpa.Tierart;

/**
 *
 * @author phoenix
 *
 * Rest für alle Tierarten
 */
@Stateless
@Path("tierlist")
public class TierartListRest {

    @EJB
    private TierartBean tierartBean;

    @EJB
    private UserBean userBean;

    @GET
    public String doGet(@HeaderParam("Authorization") String authorization) {
        int returnParam = userBean.userAuthorization(authorization);

        if (returnParam == 1) {
            List<Tierart> tierarten = this.tierartBean.findAll();
            Gson gson = new Gson();
            String json = gson.toJson(tierarten);
            return json;

        } else {
            return String.valueOf(returnParam);
        }
    }

    @GET
    @Path("tierartname/{name}")
    public String doGetName(@PathParam("name") String name, @HeaderParam("Authorization") String authorization) {
        // Anzuzeigende Tierart suchen
        int returnParam = userBean.userAuthorization(authorization);

        if (returnParam == 1) {
            List<Tierart> tierarten = this.tierartBean.findAllTierartByTierartname(name);
            Gson gson = new Gson();
            String json = gson.toJson(tierarten);
            return json;
        } else {
            return String.valueOf(returnParam);
        }
    }
}
