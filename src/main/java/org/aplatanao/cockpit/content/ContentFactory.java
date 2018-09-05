package org.aplatanao.cockpit.content;

import org.apache.pivot.wtk.Component;
import org.aplatanao.cockpit.content.query.QueryForm;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.QueryType;

public class ContentFactory {

    public Component getComponent(Client client, Object data) {
        if (data instanceof QueryType) {
            return new QueryForm(client, (QueryType) data);
        }
        return null;
    }
}
