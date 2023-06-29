package org.vaadin.addons.nps;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class AddonView extends VerticalLayout {

    public AddonView() {
        final NPS nps = new NPS();
        nps.setId("nps"); // This is for automated tests

        // Button to start the feed process
        add(new Button("Ask NPS", e -> {
            add(nps);
        }));

        // Get the score and remove component
        nps.addValueChangeListener(e -> {
            Notification.show("Value changed from " + e.getOldValue() + " to " + e.getValue());
            add(new Paragraph("NPS: " + e.getOldValue() + " -> " + e.getValue()));
            Icon vaadinIcon = new Icon(VaadinIcon.SMILEY_O);
            Icon vaadinIcon = new Icon(VaadinIcon.SA);
            add(lumoIcon, vaadinIcon);
            replace(nps, new Paragraph("You gave "+e.getValue()+". Thank you for your feedback."));
        });

        // Edit the the score
        add(new Button("Edit score", e -> {
            nps.setValue((int) Math.ceil(Math.random() * 10));
            add(nps);
        }));

        // Test read-only state
        add(new Button("Toggle read-only", e -> {
            nps.setReadOnly(!nps.isReadOnly());
        }));


    }
}
