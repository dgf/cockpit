package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeNode;

public class QueryFieldTreeNodeRenderer extends Label implements TreeView.NodeRenderer {

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        validate(); // layout label
    }

    @Override
    public void render(Object node, Sequence.Tree.Path path, int rowIndex, TreeView treeView, boolean expanded, boolean selected, TreeView.NodeCheckState checkState, boolean highlighted, boolean disabled) {
        if (node instanceof TreeNode) {
            TreeNode branch = (TreeNode) node;
            Object data = branch.getUserData();
            if (data instanceof QueryFieldTreeEntry) {
                QueryFieldTreeEntry qtf = (QueryFieldTreeEntry) data;
                setText(qtf.getField().getName());
            }
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