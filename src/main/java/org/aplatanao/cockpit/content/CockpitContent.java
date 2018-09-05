package org.aplatanao.cockpit.content;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPaneListener;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;

import java.util.HashMap;
import java.util.Map;

public class CockpitContent extends TabPane  {

    private ContentFactory factory;

    private Map<TreeNode, Component> tabs = new HashMap<>();

    public CockpitContent(ContentFactory factory) {
        this.factory = factory;
        setStyleName("content");
        setCloseable(true);
        getTabPaneListeners().add(new CockpitContentListener(tabs));
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

        Component component = factory.getComponent(getParentClient(node), node.getUserData());
        if (component != null) {
            getTabs().add(component);
            TabPane.setTabData(component, node.getText());
            setSelectedTab(component);
            tabs.put(node, component);
        }
    }

}
