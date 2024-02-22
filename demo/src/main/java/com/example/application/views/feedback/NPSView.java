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

import java.io.File;

import org.vaadin.addons.nps.NPS;

@PageTitle("Feedback")
@Route(value = "")
public class NPSView extends VerticalLayout {

    public static final String DEFAULT_LINK_TEXT = "Visit vaadin.com";
    public static final String DEFAULT_LINK = "https://vaadin.com/";
    public static final String DEFAULT_HEADER = "Thank you for visiting us.";
    public static final String DEFAULT_QUESTION = NPS.DEFAULT_NPS_QUESTION;
    public static final String DEFAULT_THANK_YOU = "Thank you. We appreciate your feedback.";

    private static final String PRODUCTION_CREDENTIAL_FILE = "/local/nps-feedback-demo";
    private static final String DEVELOPMENT_CREDENTIAL_FILE = "/workspaces/nps/.devcontainer/local/service_account_credentials.json";

    private static final String DEFAULT_SHEET_ID = "1aTfU2_XuZU-HgUhSBu4_oB_gB4hhro-RzNsdN9_8YX8";
    private static final String SHEET_LINK = "https://docs.google.com/spreadsheets/d/1aTfU2_XuZU-HgUhSBu4_oB_gB4hhro-RzNsdN9_8YX8";
    private static final String SHEET_LINK_TEXT = "See responses";

    NPS nps = new NPS();
    H2 header = new H2(DEFAULT_HEADER);
    Paragraph thankYou = new Paragraph(DEFAULT_THANK_YOU);
    Anchor closeLink = new Anchor(DEFAULT_LINK, DEFAULT_LINK_TEXT);
    Anchor sheetLink = new Anchor(SHEET_LINK, SHEET_LINK_TEXT);

    String productName = "default";

    public NPSView() {

        
        // Add all views
        add(header);
        add(nps);

        // Actions
        nps.addValueChangeListener(e -> {
            header.setVisible(false);
            replace(nps, thankYou);
            add(sheetLink);
            add(closeLink);

            String credentialFileName = getCredentialFileName();
            String sheetId = getSheetId();
            // Store the results into a Google Sheet
            FeedbackSheet feedbackSheet = new FeedbackSheet(sheetId,
                    credentialFileName);
            feedbackSheet.append(productName,"" + UI.getCurrent().hashCode(), e.getValue());
        });

        // Styling
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
    }

    /** Get the ID of the Google Sheet where to store the results.
     * 
     * @return  
    */
    protected String getSheetId() {
        return DEFAULT_SHEET_ID;
    }

    /** Find the name of credential file used to authenticate to Google Sheets.
     * 
     * By default we use Google Secret Manager to mount file in Cloud Run and local directory 
     * in development.
     * 
     */
    protected String getCredentialFileName() {

        // Try production location first
        File  f = new File(PRODUCTION_CREDENTIAL_FILE);
        if (f.exists() && f.canRead()) return PRODUCTION_CREDENTIAL_FILE;
        return DEVELOPMENT_CREDENTIAL_FILE;
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
