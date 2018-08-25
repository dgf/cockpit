package org.aplatanao.billing.cockpit.models;

import javafx.beans.binding.StringExpression;

public interface Detail {

    StringExpression titleProperty();

    Object getSource();

}
