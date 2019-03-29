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
import projectanimal.tierarten.ejb.SpeziesBean;
import projectanimal.tierarten.jpa.Spezies;

/**
 *
 * @author phoenix
 *
 * Rest für alle Spezies
 */
@Stateless
@Path("spezieslist")
public class SpeziesListRest {

    @EJB
    private SpeziesBean speziesBean;

    @EJB
    private UserBean userBean;

    /**
     *
     * @param authorization
     * @return
     */
    @GET
    public String doGet(@HeaderParam("Authorization") String authorization) {
        // Anzuzeigende Spezies suchen
        int returnParam = userBean.userAuthorization(authorization);
        if (returnParam == 1) {
            List<Spezies> spezies = this.speziesBean.findAll();
            Gson gson = new Gson();
            String json = gson.toJson(spezies);
            return json;
        } else {
            return String.valueOf(returnParam);
        }

    }

    /**
     *
     * @param name
     * @param authorization
     * @return
     */
    @GET
    @Path("{name}")
    public String doGet(@PathParam("name") String name, @HeaderParam("Authorization") String authorization) {
        int returnParam = userBean.userAuthorization(authorization);

        if (returnParam == 1) {
            // Anzuzeigende Spezies suchen
            List<Spezies> spezies = this.speziesBean.findAllSpeziesByName(name);
            Gson gson = new Gson();
            String json = gson.toJson(spezies);
            return json;
        } else {
            return String.valueOf(returnParam);
        }
    }

}
