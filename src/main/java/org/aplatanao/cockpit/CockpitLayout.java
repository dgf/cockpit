package org.aplatanao.cockpit;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.SplitPane;
import org.apache.pivot.wtk.TablePane;
import org.aplatanao.cockpit.controls.CockpitControls;
import org.aplatanao.cockpit.details.CockpitDetails;
import org.aplatanao.cockpit.main.CockpitMain;
import org.aplatanao.cockpit.menu.CockpitMenu;
import org.aplatanao.cockpit.status.CockpitStatus;

public class CockpitLayout extends TablePane {

    public CockpitLayout(
            CockpitMenu menu,
            CockpitControls controls,
            CockpitMain main,
            CockpitDetails details,
            CockpitStatus status
    ) {
        setStyleName("layout");

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
        controlsRow.add(controls);

        SplitPane split = new SplitPane(Orientation.VERTICAL);
        split.setSplitRatio(0.73f);
        split.setTop(main);
        split.setBottom(new Border(details));

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
