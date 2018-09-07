package org.aplatanao.cockpit.navigation;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.cockpit.content.CockpitContent;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;
import org.aplatanao.cockpit.overview.CockpitOverview;
import org.aplatanao.cockpit.tree.CockpitTreeListener;

public class NavigationListener extends CockpitTreeListener {

    private CockpitCrumbs crumbs;

    private CockpitOverview overview;

    private CockpitContent content;

    public NavigationListener(CockpitCrumbs crumbs, CockpitOverview overview, CockpitContent content) {
        this.crumbs = crumbs;
        this.overview = overview;
        this.content = content;
    }

    @Override
    protected void enter(List<TreeNode> nodes) {
        crumbs.show(nodes);
        overview.show(nodes);
    }

    @Override
    protected void enter(TreeNode node) {
        content.show(node);
    }

}
