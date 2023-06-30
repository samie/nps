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
public class FeedbackView extends VerticalLayout {

    NPS nps = new NPS();
    H2 header = new H2("Thank you for visiting us.");
    Paragraph thankYou = new Paragraph("We appreciate your feedback.");
    Anchor closeLink = new Anchor("https://vaadin.com/", "Go back to vaadin.com");

    public FeedbackView() {

        // Add all views
        add(header);
        add(nps);

        // Actions
        nps.addValueChangeListener(e -> {
            header.setText("Thank You");
            replace(nps, thankYou);
            add(closeLink);

            // Store the results into a Google Sheet
            FeedbackSheet feedbackSheet = new FeedbackSheet("1aTfU2_XuZU-HgUhSBu4_oB_gB4hhro-RzNsdN9_8YX8",
                    "/workspaces/nps/.devcontainer/local/service_account_credentials.json");
            feedbackSheet.append("" + UI.getCurrent().hashCode(), e.getValue());
        });

        // Styling
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        nps.setClassName("nps");
    }

}
