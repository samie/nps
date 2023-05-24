package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
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
            remove(nps);
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
