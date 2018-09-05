package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeViewNodeStateListener;

public class QueryFieldTreeNodeListener implements TreeViewNodeStateListener {

    @Override
    public void nodeCheckStateChanged(TreeView treeView, Sequence.Tree.Path path, TreeView.NodeCheckState previousCheckState) {
        ((QueryFieldTree) treeView).check(path);
    }
}
