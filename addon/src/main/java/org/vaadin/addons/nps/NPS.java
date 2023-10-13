package org.vaadin.addons.nps;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class NPS extends CustomField<Integer> {

    public static final String VERY_LIKELY = "Very likely";
    public static final String NOT_LIKELY = "Not likely";
    public static final String DEFAULT_NPS_QUESTION = "On a scale of 0 to 10, how likely would you recommend this to your friend or colleague?";
    
    private Integer score;
    private Span title;

    private HorizontalLayout buttons;

    public NPS() {
        this.title = new Span(DEFAULT_NPS_QUESTION);
        this.buttons = new HorizontalLayout();
        buttons.add(new Text(NOT_LIKELY));
        for (int i = 0; i <= 10; i++) {
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
        buttons.add(new Text(VERY_LIKELY));
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

    public Integer getScore() {
        return getValue();
    }

    public void setScore(Integer score) {
        setValue(score);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);;
    }
    
}
