package ru.jewelline.wicket.osgi.impl;

import java.io.InputStream;
import java.util.Properties;

public class BuildProperties {

    private final Properties properties;

    private BuildProperties() {
        try {
            try (InputStream propertyStream = getClass().getResourceAsStream("/build-properties.xml")) {
                properties = new Properties();
                properties.loadFromXML(propertyStream);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve build properties.", e);
        }
    }

    public static BuildProperties getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Boolean isWicketInDevelopmentStage() {
        return Boolean.parseBoolean(properties.getProperty("wicket.stage.development"));
    }

    private static class InstanceHolder {
        private static final BuildProperties INSTANCE = new BuildProperties();
    }
}
