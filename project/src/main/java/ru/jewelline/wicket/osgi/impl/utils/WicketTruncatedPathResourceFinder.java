package ru.jewelline.wicket.osgi.impl.utils;

import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;

public class WicketTruncatedPathResourceFinder implements IResourceFinder {
    private final IResourceFinder baseFinder;
    private final String truncationTemplate;

    public WicketTruncatedPathResourceFinder(IResourceFinder baseFinder, String truncationTemplate){
        this.baseFinder = baseFinder;
        this.truncationTemplate = truncationTemplate != null ? truncationTemplate : "";
    }

    @Override
    public IResourceStream find(Class<?> aClass, String s) {
        if (s != null && s.startsWith(truncationTemplate) && truncationTemplate.length() > 0) {
            return this.baseFinder.find(aClass, s.substring(truncationTemplate.length()));
        }
        return this.baseFinder.find(aClass, s);
    }
}
