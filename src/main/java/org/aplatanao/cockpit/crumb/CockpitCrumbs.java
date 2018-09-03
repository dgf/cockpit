package org.aplatanao.cockpit.crumb;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.content.TreeNode;

public class CockpitCrumbs extends BoxPane {

    private List<Crumb> path = new ArrayList<>();

    public CockpitCrumbs() {
        setStyleName("crumbs");
    }

    public void show(List<TreeNode> nodes) {
        // remove unneeded end of path
        if (path.getLength() > nodes.getLength()) {
            for (int i = path.getLength(); i > nodes.getLength(); i--) {
                Crumb crumb = path.get(i - 1);
                remove(crumb);
                path.remove(crumb);
            }
        }

        // reassign or append
        for (int i = 0; i < nodes.getLength(); i++) {
            TreeNode node = nodes.get(i);
            if (i < path.getLength()) { // reassign
                Crumb crumb = path.get(i);
                if (node.getText().equals(crumb.getName())) {
                    continue; // the same
                }
                crumb.setNode(node);
                continue;
            }
            // append
            Crumb crumb = new Crumb(node);
            path.add(crumb);
            add(crumb);
        }
    }
}
