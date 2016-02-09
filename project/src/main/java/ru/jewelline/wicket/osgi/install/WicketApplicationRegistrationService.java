package ru.jewelline.wicket.osgi.install;

import org.apache.wicket.protocol.http.WicketServlet;
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
        service = {WicketApplicationRegistrationService.class},
        property = {"osgi.command.scope=wicket", "osgi.command.function=wicketUrl"},
        immediate = true)
public class WicketApplicationRegistrationService {
    public static final String WICKET_APPLICATION_URL_PREFIX = "/wicket";

    private volatile HttpService httpService;
    private volatile WicketApplication wicketApplication;

    @SuppressWarnings("unused") // Default OSGI constructor
    public WicketApplicationRegistrationService() {
    }

    @Activate
    @SuppressWarnings("unused") // OSGI
    public void activate() {
        try {
            Dictionary<String, String> properties = new Hashtable<>();
            properties.put("applicationFactoryClassName", WicketApplication.Factory.class.getName());
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
    @SuppressWarnings("unused") // OSGI
    public void deactivate() {
        this.httpService.unregister(WICKET_APPLICATION_URL_PREFIX);
    }

    @Reference
    @SuppressWarnings("unused") // OSGI
    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

    @Reference
    @SuppressWarnings("unused") // OSGI
    public void setWicketApplication(WicketApplication wicketApplication) {
        // Guarantees that WicketApplication was instantiated
        this.wicketApplication = wicketApplication;
    }

    @SuppressWarnings("unused") // OSGI GoGo command
    public void wicketUrl() {
        System.out.println(WICKET_APPLICATION_URL_PREFIX);
    }
}
