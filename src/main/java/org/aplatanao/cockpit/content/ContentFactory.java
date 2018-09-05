package org.aplatanao.cockpit.content;

import org.apache.pivot.wtk.Component;
import org.aplatanao.cockpit.content.query.QueryContent;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.QueryType;

public class ContentFactory {

    public Component getComponent(Client client, Object data) {
        if (data instanceof QueryType) {
            return new QueryContent(client, (QueryType) data);
        }
        return null;
    }
}
