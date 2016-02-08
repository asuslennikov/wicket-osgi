package ru.jewelline.wicket.osgi.impl;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.resource.ClassPathResourceFinder;
import org.apache.wicket.protocol.http.WebApplication;
import ru.jewelline.wicket.osgi.impl.pages.Index;
import ru.jewelline.wicket.osgi.impl.utils.WicketClassResolver;
import ru.jewelline.wicket.osgi.impl.utils.WicketTruncatedPathResourceFinder;

public class WicketApplication extends WebApplication {

    public WicketApplication() {
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return Index.class;
    }

    @Override
    protected void init() {
        super.init();
        getApplicationSettings().setClassResolver(WicketClassResolver.getClassResolver());
        getResourceSettings().getResourceFinders().add(new WicketTruncatedPathResourceFinder(
                new ClassPathResourceFinder(""), this.getClass().getPackage().getName().replace('.', '/')));
        getMarkupSettings().setStripWicketTags(true);
    }
}
