package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class NPS extends CustomField<Integer> {

    private Integer score;
    private Span title;
    private HorizontalLayout buttons;

    public NPS() {
        this.title = new Span(
                "On a scale of 1 to 10, how likely would you recommend this to your friend or colleague?");
        this.buttons = new HorizontalLayout();
        buttons.add(new Text("Not likely"));
        for (int i = 1; i <= 10; i++) {
            Button button = new Button(String.valueOf(i), e -> {
                this.score = Integer.parseInt(e.getSource().getText());
                updateButtonSate();
                setModelValue(this.score, true);
            });
            button.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
            button.addClassName(LumoUtility.Padding.XSMALL);
            button.getStyle()
                    .set("min-width", " var(--lumo-button-size)")
                    .set("max-width", " var(--lumo-button-size)");
            buttons.add(button);
        }
        buttons.add(new Text("Very likely"));
        this.add(title, buttons);

        this.getStyle().set("text-align", "center");
    }

    @Override
    protected Integer generateModelValue() {
        return this.score;
    }

    @Override
    protected void setPresentationValue(Integer score) {
        this.score = score;
        updateButtonSate();
    }

    private void updateButtonSate() {
        this.buttons
                .getChildren()
                .filter(c -> c instanceof Button)
                .forEach(c -> {
                    Button b = (Button) c;
                    if (score != null && score.equals(Integer.parseInt(b.getText()))) {
                        b.addClassName(LumoUtility.Background.SUCCESS_50);
                        b.addClassName(LumoUtility.TextColor.SUCCESS_CONTRAST);
                    } else {
                        b.removeClassName(LumoUtility.Background.SUCCESS_50);
                        b.removeClassName(LumoUtility.TextColor.SUCCESS_CONTRAST);
                    }
                    ;
                });
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        this.buttons
                .getChildren()
                .filter(c -> c instanceof Button)
                .forEach(c -> ((Button) c).setEnabled(!readOnly));
    }

}
