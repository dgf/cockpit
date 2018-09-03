package org.aplatanao.cockpit.navigation;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;

public class TreeNodeRenderer extends Label implements TreeView.NodeRenderer {

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        validate(); // layout label
    }

    @Override
    public void render(Object node, Sequence.Tree.Path path, int rowIndex, TreeView treeView, boolean expanded, boolean selected, TreeView.NodeCheckState checkState, boolean highlighted, boolean disabled) {
        setStyleName("active");

        if (node instanceof TreeBranch) {
            TreeBranch branch = (TreeBranch) node;
            setText(branch.getText());
            Object data = branch.getUserData();
            if (data instanceof Client) {
                Client client = (Client) data;
                if (!client.isInitialized()) {
                    setStyleName("inactive");
                }
            }
            return;
        }

        if (node instanceof TreeNode) {
            setText(((TreeNode) node).getText());
            return;
        }

        setText(toString(node));
    }

    @Override
    public String toString(Object node) {
        if (node == null) {
            return "N/A";
        }
        return node.toString();
    }
}