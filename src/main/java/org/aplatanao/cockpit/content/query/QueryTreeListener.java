package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeViewBranchListener;
import org.apache.pivot.wtk.TreeViewNodeStateListener;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;
import org.aplatanao.cockpit.tree.CockpitTreeListener;

public class QueryTreeListener extends CockpitTreeListener implements TreeViewNodeStateListener, TreeViewBranchListener {

    private CockpitCrumbs crumbs;

    public QueryTreeListener(CockpitCrumbs crumbs) {
        this.crumbs = crumbs;
    }

    @Override
    public void nodeCheckStateChanged(TreeView treeView, Sequence.Tree.Path path, TreeView.NodeCheckState previousCheckState) {
        ((QueryTree) treeView).check(path);
    }

    @Override
    public void branchExpanded(TreeView treeView, Sequence.Tree.Path path) {
        ((QueryTree) treeView).loadSubBranchOnce(path);
    }

    @Override
    public void branchCollapsed(TreeView treeView, Sequence.Tree.Path path) {

    }

    @Override
    protected void enter(List<TreeNode> nodes) {
        crumbs.show(nodes);
    }

    @Override
    protected void enter(TreeNode node) {

    }

}
