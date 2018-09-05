package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.Comparator;

public class QueryFieldTree extends TreeView {

    private TreeBranch tree = new TreeBranch();

    private QueryForm form;

    private Client client;

    public QueryFieldTree(QueryForm form, Client client, Type type) {
        this.form = form;
        this.client = client;
        setTreeData(tree);
        setCheckmarksEnabled(true);
        setShowMixedCheckmarkState(true);
        setNodeRenderer(new QueryFieldTreeNodeRenderer());
        getTreeViewNodeStateListeners().add(new QueryFieldTreeNodeListener());
        type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> add(tree, f));
    }

    private void add(TreeBranch tree, Field field) {
        Type type = client.getType(field.getType().getName());
        if (type != null && "OBJECT".equals(type.getKind())) {
            TreeBranch branch = new TreeBranch(field.getName());
            branch.setUserData(field);
            tree.add(branch);
            loadFieldsOnce(branch, field);
            return;
        }
        TreeNode node = new TreeNode(field.getName());
        node.setUserData(field);
        tree.add(node);
    }

    private void loadFieldsOnce(TreeBranch branch, Field field) {
        if (branch.isEmpty()) {
            client.getType(field.getType().getName()).getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(f -> add(branch, f));
        }
    }

    public void check(Sequence.Tree.Path path) {
        TreeNode changed = Sequence.Tree.get(tree, path);
        Field field = (Field) changed.getUserData();
        if (changed instanceof TreeBranch) {
            TreeBranch branch = (TreeBranch) changed;
            System.out.println("add branch: " + branch.getText() + ", field: " + field.getName());
            return;
        }
        System.out.println("add node: " + changed.getText());
    }

}
