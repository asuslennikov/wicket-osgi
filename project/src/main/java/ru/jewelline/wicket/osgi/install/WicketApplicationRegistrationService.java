package ru.jewelline.wicket.osgi.install;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import ru.jewelline.wicket.osgi.impl.WicketApplication;

import javax.servlet.ServletException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Logger;

@Component(name = "ru.jewelline.wicket.osgi.install.WicketApplicationRegistrationService",
        service = {WicketApplicationRegistrationService.class}, // Or GoGo will not find our command
        property = {"osgi.command.scope=wicket", "osgi.command.function=wicketBasePath"},
        immediate = true)
public class WicketApplicationRegistrationService {
    public static final String WICKET_APPLICATION_URL_PREFIX = "/wicket";

    private volatile HttpService httpService;

    @SuppressWarnings("unused")
    // Default OSGI constructor
    public WicketApplicationRegistrationService() {
    }

    @Activate
    public void activate(BundleContext context) {
        try {
            Dictionary properties = new Hashtable<>();
            properties.put("applicationClassName", WicketApplication.class.getName());
            properties.put("filterMappingUrlPattern", WICKET_APPLICATION_URL_PREFIX + "/*");
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(WicketApplicationRegistrationService.class.getClassLoader());
            this.httpService.registerServlet(WICKET_APPLICATION_URL_PREFIX, new WicketServlet(), properties, null);
            Thread.currentThread().setContextClassLoader(ccl);
        } catch (ServletException | NamespaceException e) {
            Logger.getLogger("Wicket registration fail: " + this.getClass().getName()).severe(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Deactivate
    public void deactivate() {
        this.httpService.unregister(WICKET_APPLICATION_URL_PREFIX);
    }

    @Reference
    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

    public void wicketBasePath() {
        System.out.println(WICKET_APPLICATION_URL_PREFIX);
    }
}
