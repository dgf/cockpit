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

    private Field field;

    private Type type;

    public QueryFieldTree(QueryForm form, Client client, Field field) {
        this.form = form;
        this.client = client;
        this.field = field;
        this.type = client.getType(field.getType().getName());

        setTreeData(tree);
        setCheckmarksEnabled(true);
        setShowMixedCheckmarkState(true);
        setNodeRenderer(new QueryFieldTreeNodeRenderer());
        QueryFieldTreeNodeListener listener = new QueryFieldTreeNodeListener();
        getTreeViewNodeStateListeners().add(listener);
        getTreeViewBranchListeners().add(listener);

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
            return;
        }
        TreeNode node = new TreeNode(field.getName());
        node.setUserData(field);
        tree.add(node);
    }

    public void loadSubBranchOnce(Sequence.Tree.Path path) {
        TreeBranch branch = (TreeBranch) Sequence.Tree.get(tree, path);
        if (branch.isEmpty()) {
            Field field = (Field) branch.getUserData();

            client.getType(field.getType().getName()).getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(f -> {
                        try {
                            add(branch, f);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
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
