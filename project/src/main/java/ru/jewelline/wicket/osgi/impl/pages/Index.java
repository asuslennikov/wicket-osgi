package ru.jewelline.wicket.osgi.impl.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;

public class Index extends WebPage {
    public Index() {
        Label helloMessageLabel = new Label("helloMessage", "Hello WicketWorld!");
        helloMessageLabel.setOutputMarkupId(true);
        add(helloMessageLabel);

        add(new AjaxLink("ajaxLink") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                helloMessageLabel.setDefaultModelObject("Hello WicketWorld from Ajax!");
                ajaxRequestTarget.add(helloMessageLabel);
            }
        });
    }
}
