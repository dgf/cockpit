package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;

public class QueryFieldTreeNodeListener implements ComponentKeyListener, TreeViewNodeStateListener {

    @Override
    public boolean keyTyped(Component component, char character) {
        return false;
    }

    @Override
    public boolean keyPressed(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
        return false;
    }

    @Override
    public boolean keyReleased(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
        if (keyCode == Keyboard.KeyCode.SPACE) {
            QueryFieldTree tree = (QueryFieldTree) component;
            Sequence.Tree.Path path = tree.getSelectedPath();
            Object selected = tree.getSelectedNode();
            if (selected instanceof TreeBranch) {
                TreeBranch branch = (TreeBranch) selected;
                QueryFieldTreeEntry data = (QueryFieldTreeEntry) branch.getUserData();
                if (data.isChecked()) {
                    data.setChecked(false);
                } else {
                    data.setChecked(true);
                    tree.loadFieldsOnce(branch, data.getField());
                    tree.setBranchExpanded(path, true);
                }
            }
            if (selected instanceof TreeNode) {
                TreeNode node = (TreeNode) selected;
                QueryFieldTreeEntry data = (QueryFieldTreeEntry) node.getUserData();
                if (data.isChecked()) {
                    data.setChecked(false);
                } else {
                    data.setChecked(true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void nodeCheckStateChanged(TreeView treeView, Sequence.Tree.Path path, TreeView.NodeCheckState previousCheckState) {
        // TODO add/hide/show parameters
    }
}
