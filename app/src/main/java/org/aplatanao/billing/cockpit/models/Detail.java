package org.aplatanao.billing.cockpit.models;

import javafx.beans.binding.StringExpression;
import javafx.scene.Node;

public interface Detail {

    StringExpression titleProperty();

    Object getSource();

    Node getActions();
}
