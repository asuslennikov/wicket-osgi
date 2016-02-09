package ru.jewelline.wicket.osgi.services;

import org.osgi.service.component.annotations.Component;
import ru.jewelline.wicket.osgi.MessageService;

@Component(name = "ru.jewelline.wicket.osgi.install.MessageService",
        service = {MessageService.class},
        property = {"osgi.command.scope=wicket", "osgi.command.function=setGreetingMessage"},
        immediate = true)
public class MessageServiceImpl implements MessageService {

    private volatile String greetingMessage = "Hello WicketWorld from Ajax!";

    @Override
    public String getGreetingMessage() {
        return this.greetingMessage;
    }

    /**
     * Sets the new greeting message which will be displayed for user.
     * @param newGreetingMessage a message.
     */
    public void setGreetingMessage(String newGreetingMessage) {
        this.greetingMessage = newGreetingMessage;
    }
}
