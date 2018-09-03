package org.aplatanao.cockpit.crumb;

import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.content.TreeNode;

public class Crumb extends PushButton {

    private TreeNode node;

    public Crumb(TreeNode node) {
        setNode(node);
    }

    public Crumb setNode(TreeNode node) {
        this.node = node;
        setButtonData(node.getText());
        return this;
    }
}
