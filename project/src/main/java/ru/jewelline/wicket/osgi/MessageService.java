package ru.jewelline.wicket.osgi;

/**
 * Simple OSGI service which provides a greeting message.
 */
public interface MessageService {
    /**
     * @return The current greeting message.
     */
    String getGreetingMessage();
}
