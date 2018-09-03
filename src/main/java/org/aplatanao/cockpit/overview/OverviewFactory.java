package org.aplatanao.cockpit.overview;

import javafx.beans.property.ObjectProperty;
import org.apache.pivot.wtk.Form;
import org.aplatanao.cockpit.graphql.Client;
import org.aplatanao.cockpit.graphql.Query;
import org.aplatanao.cockpit.graphql.Type;

public class OverviewFactory {

    public Form getForm(Object data) {
        if (data instanceof Client) {
            return new ClientForm();
        }
        if (data instanceof Query) {
            return new QueryForm();
        }
        if (data instanceof Type) {
            return new TypeForm();
        }
        return null;
    }
}
