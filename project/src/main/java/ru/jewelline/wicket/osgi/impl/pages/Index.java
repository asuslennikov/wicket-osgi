package ru.jewelline.wicket.osgi.impl.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import ru.jewelline.wicket.osgi.MessageService;

import javax.inject.Inject;

public class Index extends WebPage {

    @Inject
    private MessageService messageService;

    public Index() {
        Label helloMessageLabel = new Label("helloMessage", "Hello WicketWorld!");
        helloMessageLabel.setOutputMarkupId(true);
        add(helloMessageLabel);

        add(new AjaxLink("ajaxLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                helloMessageLabel.setDefaultModelObject(messageService.getGreetingMessage());
                ajaxRequestTarget.add(helloMessageLabel);
            }
        });
    }
}
