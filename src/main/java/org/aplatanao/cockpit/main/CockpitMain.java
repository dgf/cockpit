package org.aplatanao.cockpit.main;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.SplitPane;
import org.aplatanao.cockpit.content.CockpitContent;
import org.aplatanao.cockpit.navigation.CockpitNavigation;
import org.aplatanao.cockpit.overview.CockpitOverview;

public class CockpitMain extends Border {

    public CockpitMain(CockpitNavigation navigation, CockpitContent content, CockpitOverview overview) {
        setStyleName("main");

        ScrollPane navigationScroll = new ScrollPane();
        navigationScroll.setView(navigation);

        ScrollPane overviewScroll = new ScrollPane();
        overviewScroll.setView(overview);

        SplitPane right = new SplitPane();
        right.setSplitRatio(0.57f);
        right.setLeft(new Border(content));
        right.setRight(new Border(overviewScroll));

        SplitPane left = new SplitPane();
        left.setSplitRatio(0.23f);
        left.setLeft(new Border(navigationScroll));
        left.setRight(right);
        setContent(left);
    }
}
