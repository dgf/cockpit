package org.aplatanao.cockpit;

import org.apache.pivot.wtk.*;
import org.aplatanao.cockpit.main.CockpitMain;
import org.aplatanao.cockpit.menu.CockpitMenu;
import org.aplatanao.cockpit.navigation.CockpitNavigation;
import org.aplatanao.cockpit.status.CockpitStatus;
import org.aplatanao.cockpit.view.CockpitView;

public class CockpitLayout extends TablePane {

    public CockpitLayout(
            CockpitMenu menu,
            CockpitNavigation navigation,
            CockpitMain main,
            CockpitView view,
            CockpitStatus status
    ) {
        Column column = new Column();
        column.setWidth("1*");
        getColumns().add(column);

        Row menuRow = new Row();
        menuRow.setHeight(-1);
        getRows().add(menuRow);
        menuRow.add(menu);

        Row controlsRow = new Row();
        controlsRow.setHeight(-1);
        getRows().add(controlsRow);
        controlsRow.add(navigation);

        SplitPane split = new SplitPane(Orientation.VERTICAL);
        split.setSplitRatio(0.73f);
        split.setTop(main);
        split.setBottom(new Border(view));

        Row mainRow = new Row();
        mainRow.setHeight("1*");
        mainRow.add(split);
        getRows().add(mainRow);

        Row statusRow = new Row();
        statusRow.setHeight(-1);
        getRows().add(statusRow);
        statusRow.add(status);
    }
}
