package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeViewBranchListener;
import org.apache.pivot.wtk.TreeViewNodeStateListener;

public class QueryFieldTreeNodeListener implements TreeViewNodeStateListener, TreeViewBranchListener {

    @Override
    public void nodeCheckStateChanged(TreeView treeView, Sequence.Tree.Path path, TreeView.NodeCheckState previousCheckState) {
        ((QueryFieldTree) treeView).check(path);
    }

    @Override
    public void branchExpanded(TreeView treeView, Sequence.Tree.Path path) {
        ((QueryFieldTree) treeView).loadSubBranchOnce(path);
    }

    @Override
    public void branchCollapsed(TreeView treeView, Sequence.Tree.Path path) {

    }
}
