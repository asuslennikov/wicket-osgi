package ru.jewelline.wicket.osgi.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.apache.wicket.Page;
import org.apache.wicket.core.util.resource.ClassPathResourceFinder;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import ru.jewelline.wicket.osgi.MessageService;
import ru.jewelline.wicket.osgi.impl.pages.Index;
import ru.jewelline.wicket.osgi.impl.utils.WicketClassResolver;
import ru.jewelline.wicket.osgi.impl.utils.WicketTruncatedPathResourceFinder;

import java.util.concurrent.atomic.AtomicBoolean;

@Component(name = "ru.jewelline.wicket.osgi.impl.WicketApplication",
        service = {WicketApplication.class},
        immediate = true)
public class WicketApplication extends WebApplication {
    private static volatile WicketApplication INSTANCE;
    private static volatile AtomicBoolean isInstantiated = new AtomicBoolean(Boolean.FALSE);

    private volatile MessageService messageService;

    public WicketApplication() {
        if (!isInstantiated.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            throw new IllegalStateException("WicketApplication must be instantiated only once.");
        }
        WicketApplication.INSTANCE = this;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return Index.class;
    }

    @Override
    protected void init() {
        super.init();
        getApplicationSettings().setClassResolver(WicketClassResolver.getClassResolver());
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, getInjectionModule()));
        getResourceSettings().getResourceFinders().add(new WicketTruncatedPathResourceFinder(
                new ClassPathResourceFinder(""), this.getClass().getPackage().getName().replace('.', '/')));
        getMarkupSettings().setStripWicketTags(true);
    }

    @Reference
    @SuppressWarnings("unused") // OSGI
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    private Module getInjectionModule() {
        return new AbstractModule() {
            @Override
            public void configure() {
                bind(MessageService.class).toInstance(WicketApplication.this.messageService);
            }
        };
    }

    public static class Factory implements IWebApplicationFactory {
        @Override
        public WebApplication createApplication(WicketFilter wicketFilter) {
            if (!isInstantiated.get()) {
                throw new IllegalStateException("WicketApplication should be already initialized.");
            }
            return WicketApplication.INSTANCE;
        }

        @Override
        public void destroy(WicketFilter wicketFilter) {
            // nothing to do
        }
    }
}
