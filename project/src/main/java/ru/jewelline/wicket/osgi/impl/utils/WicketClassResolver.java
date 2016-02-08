package ru.jewelline.wicket.osgi.impl.utils;

import org.apache.wicket.Application;
import org.apache.wicket.application.CompoundClassResolver;
import org.apache.wicket.application.IClassResolver;

public final class WicketClassResolver {
    private WicketClassResolver() {
        // utilities constructor
    }

    public static IClassResolver getClassResolver() {
        CompoundClassResolver classResolver = new CompoundClassResolver();
        classResolver.add(new DelegatedClassResolver(Application.class.getClassLoader()));
        classResolver.add(new DelegatedClassResolver(Application.get().getClass().getClassLoader()));
        classResolver.add(new DelegatedClassResolver(Thread.currentThread().getContextClassLoader()));
        return classResolver;
    }
}
