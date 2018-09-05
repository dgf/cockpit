package org.aplatanao.cockpit.content;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPaneListener;
import org.apache.pivot.wtk.content.TreeNode;

import java.util.Map;

public class CockpitContentListener implements TabPaneListener {

    private Map<TreeNode, Component> tabs;

    public CockpitContentListener(Map<TreeNode, Component> tabs) {
        this.tabs = tabs;
    }

    @Override
    public void tabInserted(TabPane tabPane, int index) {

    }

    @Override
    public Vote previewRemoveTabs(TabPane tabPane, int index, int count) {
        return Vote.APPROVE;
    }

    @Override
    public void removeTabsVetoed(TabPane tabPane, Vote reason) {

    }

    @Override
    public void tabsRemoved(TabPane tabPane, int index, Sequence<Component> tabs) {
        for (int t = 0; t < tabs.getLength(); t++) {
            this.tabs.values().remove(tabs.get(t));
        }
    }

    @Override
    public void cornerChanged(TabPane tabPane, Component previousCorner) {

    }

    @Override
    public void tabDataRendererChanged(TabPane tabPane, Button.DataRenderer previousTabDataRenderer) {

    }

    @Override
    public void closeableChanged(TabPane tabPane) {

    }

    @Override
    public void collapsibleChanged(TabPane tabPane) {

    }
}
