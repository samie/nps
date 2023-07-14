package com.example.application;

import com.example.application.views.feedback.FeedbackView;
import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;

public class MicroFrontendExporter extends WebComponentExporter<FeedbackView> {

    protected MicroFrontendExporter() {
        super("nps-feedback");
        addProperty("product", "")
                .onChange(FeedbackView::setProductName);
    }

    @Override
    protected void configureInstance(WebComponent<FeedbackView> webComponent, FeedbackView component) {
        // Nothing to configure here
    }
    
}
