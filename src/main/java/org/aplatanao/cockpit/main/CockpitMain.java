package org.aplatanao.cockpit.main;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.SplitPane;
import org.aplatanao.cockpit.overview.CockpitOverview;
import org.aplatanao.cockpit.tree.CockpitTree;

public class CockpitMain extends Border {

    public CockpitMain(CockpitTree tree, CockpitContent content, CockpitOverview details) {
        setStyleName("main");

        ScrollPane treeScroll = new ScrollPane();
        treeScroll.setView(tree);

        ScrollPane detailsScroll = new ScrollPane();
        detailsScroll.setView(details);

        SplitPane right = new SplitPane();
        right.setSplitRatio(0.51f);
        right.setLeft(new Border(content));
        right.setRight(new Border(detailsScroll));

        SplitPane left = new SplitPane();
        left.setSplitRatio(0.23f);
        left.setLeft(new Border(treeScroll));
        left.setRight(right);
        setContent(left);
    }
}
