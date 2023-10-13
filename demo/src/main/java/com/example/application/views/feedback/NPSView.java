package com.example.application.views.feedback;

import com.example.application.data.FeedbackSheet;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import org.vaadin.addons.nps.NPS;

@PageTitle("Feedback")
@Route(value = "")
public class NPSView extends VerticalLayout {

    public static final String DEFAULT_LINK_TEXT = "Go back to vaadin.com";
    public static final String DEFAULT_LINK = "https://vaadin.com/";
    public static final String DEFAULT_HEADER = "Thank you for visiting us.";
    public static final String DEFAULT_QUESTION = NPS.DEFAULT_NPS_QUESTION;
    public static final String DEFAULT_THANK_YOU = "Thank you. We appreciate your feedback.";

    NPS nps = new NPS();
    H2 header = new H2(DEFAULT_HEADER);
    Paragraph thankYou = new Paragraph(DEFAULT_THANK_YOU);
    Anchor closeLink = new Anchor(DEFAULT_LINK, DEFAULT_LINK_TEXT);

    String productName = "default";

    public NPSView() {

        
        // Add all views
        add(header);
        add(nps);

        // Actions
        nps.addValueChangeListener(e -> {
            header.setVisible(false);
            replace(nps, thankYou);
            add(closeLink);

            // Store the results into a Google Sheet
            FeedbackSheet feedbackSheet = new FeedbackSheet("1aTfU2_XuZU-HgUhSBu4_oB_gB4hhro-RzNsdN9_8YX8",
                    "/workspaces/nps/.devcontainer/local/service_account_credentials.json");
            feedbackSheet.append(productName,"" + UI.getCurrent().hashCode(), e.getValue());
        });

        // Styling
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getHeader() {
        return this.header.getText();
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public String getQuestion() {
        return this.nps.getTitle();
    }

    public void setQuestion(String title) {
        this.nps.setTitle(title);
    }

    public String getLink() {
        return this.closeLink.getHref();
    }

    public void setLink(String link) {
        if (link != null && !link.isEmpty()) {
            this.closeLink.setVisible(true);
            this.closeLink.setHref(link);
        } else {
            this.closeLink.setVisible(false);
        }
    }

    public String getLinkText() {
        return this.closeLink.getText();
    }

    public void setLinkText(String linkText) {
        this.closeLink.setText(linkText);
    }
    
}