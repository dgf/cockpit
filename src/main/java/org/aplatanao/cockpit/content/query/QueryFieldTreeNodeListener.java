package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;

public class QueryFieldTreeNodeListener implements TreeViewSelectionListener, ComponentKeyListener {

    @Override
    public void selectedPathAdded(TreeView treeView, Sequence.Tree.Path path) {
    }

    @Override
    public void selectedPathRemoved(TreeView treeView, Sequence.Tree.Path path) {
    }

    @Override
    public void selectedPathsChanged(TreeView treeView, Sequence<Sequence.Tree.Path> previousSelectedPaths) {
    }

    @Override
    public void selectedNodeChanged(TreeView treeView, Object previousSelectedNode) {
    }

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
            if (component instanceof QueryFieldTree) {
                QueryFieldTree tree = (QueryFieldTree) component;
                Sequence.Tree.Path path = tree.getSelectedPath();
                Object selected = tree.getSelectedNode();
                if (selected instanceof TreeNode) {
                    TreeNode node = (TreeNode) selected;
                    QueryFieldTreeEntry data = (QueryFieldTreeEntry) node.getUserData();
                    if (data.isChecked()) {
                        data.setChecked(false);
                        // TODO remove form parts
                        tree.setBranchExpanded(path, false);
                    } else {
                        data.setChecked(true);
                        if (selected instanceof TreeBranch) {
                            tree.setBranchExpanded(path, true);
                        }
                        // TODO add form parts
                    }
                }
                return true;
            }
        }
        return false;
    }
}
