package org.aplatanao.cockpit.controls;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.TablePane;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;

public class CockpitControls extends TablePane {

    public CockpitControls(CockpitCrumbs crumbs, CockpitActions actions) {
        setStyleName("controls");

        Column stretch = new Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        Column right = new Column();
        right.setWidth(-1);
        getColumns().add(right);

        Row row = new Row();
        row.setHeight(-1);
        getRows().add(row);
        row.add(crumbs);
        row.add(actions);
    }
}
