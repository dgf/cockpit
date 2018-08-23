package org.aplatanao.billing.cockpit.control;

import com.dooapp.fxform.FXForm;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import org.aplatanao.billing.cockpit.models.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class Details extends Accordion {

    private Log log;

    private Map<StringExpression, TitledPane> details = new HashMap<>();

    @Autowired
    public Details(Log log) {
        this.log = log;
    }


    public void open(Detail detail, Supplier activate) {
        StringExpression titleProperty = detail.titleProperty();
        log.info("open detail " + titleProperty.get());

        TitledPane pane = details.get(titleProperty);
        if (pane == null) {
            pane = new TitledPane();
            pane.textProperty().bind(titleProperty);
            pane.setContent(new FXForm<>(detail));
            pane.expandedProperty().addListener((observable, wasExpanded, isNowExpanded) -> {
                if (isNowExpanded) {
                    activate.get();
                }
            });

            details.put(titleProperty, pane);
            getPanes().add(pane);
        }
        setExpandedPane(pane);
    }

}
