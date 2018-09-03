package org.aplatanao.cockpit.main;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Query;

public class CockpitContent extends TabPane {

    public CockpitContent() {
        setStyleName("content");
    }

    public void show(TreeNode node) {
        Object data = node.getUserData();

        Form form = null;
        if (data instanceof Query) {

        }

        if (form != null) {
            getTabs().add(form);
            TabPane.setTabData(form, node.getText());
        }
    }
}
