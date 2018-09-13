package org.aplatanao.cockpit.content;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPaneListener;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.content.query.QueryContent;
import org.aplatanao.dimpl.Context;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.HashMap;
import java.util.Map;

public class CockpitContent extends TabPane implements TabPaneListener {

    private Map<TreeNode, Component> tabs = new HashMap<>();

    private Context context;

    public CockpitContent(Context context) {
        this.context = context;
        setStyleName("content");
        setCloseable(true);
        getTabPaneListeners().add(this);
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
            //reverse.put(component, node);
        }

    }

    @Override
    public void tabInserted(TabPane tabPane, int index) {

    }

    @Override
    public Vote previewRemoveTabs(TabPane tabPane, int index, int count) {
        return Vote.APPROVE;
    }

    @Override
    public void removeTabsVetoed(TabPane tabPane, Vote reason) {

    }

    @Override
    public void tabsRemoved(TabPane tabPane, int index, Sequence<Component> tabs) {
        for (int t = 0; t < tabs.getLength(); t++) {
            this.tabs.values().remove(tabs.get(t));
        }
        // activate the last one - TBD detect and activate the next or previous one
        setSelectedTab(this.get(this.getTabs().getLength() - 1));
    }

    @Override
    public void cornerChanged(TabPane tabPane, Component previousCorner) {

    }

    @Override
    public void tabDataRendererChanged(TabPane tabPane, Button.DataRenderer previousTabDataRenderer) {

    }

    @Override
    public void closeableChanged(TabPane tabPane) {

    }

    @Override
    public void collapsibleChanged(TabPane tabPane) {

    }
}
