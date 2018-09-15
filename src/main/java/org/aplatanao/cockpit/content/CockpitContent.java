package org.aplatanao.cockpit.content;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.content.query.QueryContent;
import org.aplatanao.dimpl.Context;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.HashMap;
import java.util.Map;

public class CockpitContent extends TabPane {

    private Map<TreeNode, Component> tabs = new HashMap<>();

    private Context context;

    public CockpitContent(Context context) {
        this.context = context;
        setStyleName("content");
        setCloseable(true);
    }

    private Client getParentClient(TreeNode node) {
        Object data = node.getUserData();
        if (data instanceof Client) {
            return (Client) data;
        }
        TreeBranch parent = node.getParent();
        if (parent == null) {
            return null;
        }
        return getParentClient(parent);
    }

    public void show(TreeNode node) {
        if (tabs.containsKey(node)) {
            setSelectedTab(tabs.get(node));
            return;
        }

        Client client = getParentClient(node);
        if (client == null) {
            return;
        }

        Object data = node.getUserData();
        if (data instanceof Field) {
            Field field = (Field) data;

            Context factory = context.createFactory();
            factory.put(Client.class, client);
            factory.put(Field.class, field);
            factory.put(Type.class, client.getType(field.getType().getName()));

            QueryContent component = factory.get(QueryContent.class);
            getTabs().add(component);
            TabPane.setTabData(component, node.getText());
            setSelectedTab(component);
            tabs.put(node, component);
        }
    }
}
