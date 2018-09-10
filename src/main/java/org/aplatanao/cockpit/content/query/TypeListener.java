package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeViewBranchListener;
import org.apache.pivot.wtk.TreeViewNodeStateListener;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.tree.CockpitTreeListener;

public class TypeListener extends CockpitTreeListener implements TreeViewNodeStateListener, TreeViewBranchListener {

    @Override
    public void nodeCheckStateChanged(TreeView treeView, Sequence.Tree.Path path, TreeView.NodeCheckState previousCheckState) {
        ((TypeTree) treeView).check(path);
    }

    @Override
    public void branchExpanded(TreeView treeView, Sequence.Tree.Path path) {
        ((TypeTree) treeView).loadSubBranchOnce(path);
    }

    @Override
    public void branchCollapsed(TreeView treeView, Sequence.Tree.Path path) {

    }

    @Override
    protected void enter(List<TreeNode> nodes) {

    }

    @Override
    protected void enter(TreeNode node) {

    }
}
