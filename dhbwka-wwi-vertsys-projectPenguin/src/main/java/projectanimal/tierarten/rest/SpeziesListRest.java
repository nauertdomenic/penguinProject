package projectanimal.tierarten.rest;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import projectanimal.common.ejb.UserBean;
import projectanimal.common.jpa.User;
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

    @EJB
    private UserBean userBean;

    @GET
    public String doGet(@HeaderParam("Authorization") String authorization) {
        // Anzuzeigende Spezies suchen
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            // Error: 0 = kein User; 2 = falsches Passwort
            List<User> user = userBean.findUser(values[0]);
            if (user.size() < 1) {
                return "3";
            }

            if (user.get(0).checkPassword(values[1])) {
                List<Spezies> spezies = this.speziesBean.findAll();
                Gson gson = new Gson();
                String json = gson.toJson(spezies);
                return json;
            } else {
                return "2";
            }
        }

        return null;
    }

}
