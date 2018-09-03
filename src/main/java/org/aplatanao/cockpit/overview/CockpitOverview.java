package org.aplatanao.cockpit.overview;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Query;
import org.aplatanao.graphql.Type;

public class CockpitOverview extends BoxPane {

    private List<Form> path = new ArrayList<>();

    private OverviewFactory factory;

    public CockpitOverview(OverviewFactory factory) {
        this.factory = factory;
        setStyleName("details");
        setOrientation(Orientation.VERTICAL);
    }

    public void show(List<TreeNode> nodes) {

        // remove unneeded end of path
        if (path.getLength() > nodes.getLength()) {
            for (int i = path.getLength(); i > nodes.getLength(); i--) {
                Form form = path.get(i - 1);
                remove(form);
                path.remove(form);
            }
        }

        // reassign or append
        for (int i = 0; i < nodes.getLength(); i++) {
            TreeNode node = nodes.get(i);
            Object data = node.getUserData();
            if (i < path.getLength()) {
                Form form = path.get(i);
                if (reassign(form, data)) { // reassign
                    form.load(data);
                    continue;
                }
                remove(form);
            }

            // append
            Form form = factory.getForm(data);
            if (form != null) {
                form.load(data);
                path.add(form);
                add(form);
            }
        }
    }

    private Boolean reassign(Form form, Object data) {
        if (data instanceof Client && "client".equals(form.getName())) {
            return true;
        }
        if (data instanceof Query && "query".equals(form.getName())) {
            return true;
        }
        if (data instanceof Type && "type".equals(form.getName())) {
            return true;
        }
        return false;
    }

}
