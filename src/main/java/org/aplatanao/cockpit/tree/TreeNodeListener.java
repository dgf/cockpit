package org.aplatanao.cockpit.tree;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;
import org.aplatanao.cockpit.graphql.Query;
import org.aplatanao.cockpit.main.CockpitContent;
import org.aplatanao.cockpit.overview.CockpitOverview;

public class TreeNodeListener implements TreeViewSelectionListener, ComponentKeyListener {

    private CockpitCrumbs crumbs;

    private CockpitOverview overview;

    private CockpitContent content;

    public TreeNodeListener(CockpitCrumbs crumbs, CockpitOverview overview, CockpitContent content) {
        this.crumbs = crumbs;
        this.overview = overview;
        this.content = content;
    }

    @Override
    public void selectedPathAdded(TreeView treeView, Sequence.Tree.Path path) {
    }

    @Override
    public void selectedPathRemoved(TreeView treeView, Sequence.Tree.Path path) {
    }

    @Override
    public void selectedPathsChanged(TreeView treeView, Sequence<Sequence.Tree.Path> previousSelectedPaths) {
        Sequence.Tree.Path path = treeView.getSelectedPath();
        List<TreeNode> nodes = new ArrayList<>();

        List<?> treeData = treeView.getTreeData();
        for (Integer i : path) {
            Object root = treeData.get(i);
            if (root instanceof TreeBranch) {
                treeData = (List<?>) root;
            }
            nodes.add((TreeNode) root);
        }

        crumbs.show(nodes);
        overview.show(nodes);
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
        System.out.println("key released: " + keyCode);
        if (keyCode == Keyboard.KeyCode.ENTER) {
            if (component instanceof CockpitTree) {
                TreeView tree = (TreeView) component;
                Sequence.Tree.Path path = tree.getSelectedPath();
                Object selected = tree.getSelectedNode();

                if (selected instanceof TreeBranch) {
                    boolean expanded = true;
                    if (tree.isBranchExpanded(path)) {
                        expanded = false;
                    }
                    tree.setBranchExpanded(path, expanded);
                    return true;
                }
                if (selected instanceof TreeNode) {
                    content.show((TreeNode) selected);
                    return true;
                }
            }
        }
        return false;
    }
}
