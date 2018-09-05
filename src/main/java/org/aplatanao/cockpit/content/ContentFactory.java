package org.aplatanao.cockpit.content;

import org.apache.pivot.wtk.Component;
import org.aplatanao.cockpit.content.query.QueryContent;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;

public class ContentFactory {

    public Component getComponent(Client client, Object data) {
        if (data instanceof Field) {
            return new QueryContent(client, (Field) data);
        }
        return null;
    }
}
