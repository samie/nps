package com.example.application;

import com.example.application.views.feedback.NPSView;
import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;

public class CustomElementExporter extends WebComponentExporter<NPSView> {

    public CustomElementExporter() {        
        super("nps-feedback");
        addProperty("product", "default").onChange(NPSView::setProductName);
        addProperty("header", NPSView.DEFAULT_HEADER).onChange(NPSView::setHeader);
        addProperty("question", NPSView.DEFAULT_QUESTION).onChange(NPSView::setQuestion);
        addProperty("link", NPSView.DEFAULT_LINK).onChange(NPSView::setLink);
        addProperty("linktext", NPSView.DEFAULT_LINK_TEXT).onChange(NPSView::setLinkText);
    }

    @Override
    protected void configureInstance(WebComponent<NPSView> webComponent, NPSView component) {
        // not needed.
    }
    
}
