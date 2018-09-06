package org.aplatanao.cockpit.navigation;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;

public class NavigationRenderer extends Label implements TreeView.NodeRenderer {

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        validate(); // layout label
    }

    @Override
    public void render(Object node, Sequence.Tree.Path path, int rowIndex, TreeView treeView, boolean expanded, boolean selected, TreeView.NodeCheckState checkState, boolean highlighted, boolean disabled) {
        if (node == null) {
            return;
        }
        setStyleName("active");
        TreeNode treeNode = (TreeNode) node;
        setText(treeNode.getText());
        Object data = treeNode.getUserData();
        if (data instanceof Client) {
            Client client = (Client) data;
            if (!client.isInitialized()) {
                setStyleName("inactive");
            }
        }
    }


    @Override
    public String toString(Object node) {
        throw new IllegalStateException();
    }
}