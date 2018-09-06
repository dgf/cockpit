package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

public class QueryTreeRenderer extends Label implements TreeView.NodeRenderer {

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
        if (node instanceof TreeNode) {
            setText(((TreeNode) node).getText());
            return;
        }
        throw new IllegalStateException();
    }

    @Override
    public String toString(Object node) {
        throw new IllegalStateException();
    }
}