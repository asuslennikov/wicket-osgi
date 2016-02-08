package ru.jewelline.wicket.osgi.impl.utils;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.AbstractClassResolver;
import org.apache.wicket.application.IClassResolver;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class DelegatedClassResolver extends AbstractClassResolver implements IClassResolver {
    private final ClassLoader classLoader;

    DelegatedClassResolver(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public Iterator<URL> getResources(String name) {
        Set<URL> unorderResourceSet = new HashSet<>();
        try {
            Enumeration<URL> resources = getClassLoader().getResources(name);
            if (resources != null) {
                while (resources.hasMoreElements()) {
                    unorderResourceSet.add(resources.nextElement());
                }
            }
        } catch (Exception ex) {
            throw new WicketRuntimeException(ex);
        }
        return unorderResourceSet.iterator();
    }
}
