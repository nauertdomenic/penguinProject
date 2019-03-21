package projectanimal.whatever.rest;

/**
 *
 * @author simon
 */
import java.util.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.*;

@ApplicationPath("RestAPI")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(projectanimal.whatever.rest.TierartnameRest.class);
        resources.add(projectanimal.whatever.rest.TierartListRest.class);
        resources.add(projectanimal.whatever.rest.SpeziesListRest.class);
        resources.add(projectanimal.whatever.rest.SpeziesnameRest.class);

        return resources;
    }

}
