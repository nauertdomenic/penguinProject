package projectanimal.tierarten.rest;

/**
 *
 * @author phoenix
 *
 * Config für Rest-Schnittstelle
 */
import java.util.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.*;

@ApplicationPath("RestAPI")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(projectanimal.tierarten.rest.TierartnameRest.class);
        resources.add(projectanimal.tierarten.rest.TierartListRest.class);
        resources.add(projectanimal.tierarten.rest.SpeziesListRest.class);
        resources.add(projectanimal.tierarten.rest.SpeziesnameRest.class);
        resources.add(projectanimal.tierarten.rest.LoginRest.class);

        return resources;
    }

}
