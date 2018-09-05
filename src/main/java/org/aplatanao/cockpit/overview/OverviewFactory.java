package org.aplatanao.cockpit.overview;

import org.apache.pivot.wtk.Form;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.QueryType;
import org.aplatanao.graphql.Type;

public class OverviewFactory {

    public Form getForm(Object data) {
        if (data instanceof Client) {
            return new ClientForm();
        }
        if (data instanceof QueryType) {
            return new QueryForm();
        }
        if (data instanceof Type) {
            return new TypeForm();
        }
        return null;
    }
}
