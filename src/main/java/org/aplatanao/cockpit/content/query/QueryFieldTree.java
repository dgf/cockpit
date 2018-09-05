package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.Comparator;

public class QueryFieldTree extends TreeView {

    private TreeBranch tree = new TreeBranch();

    private Client client;

    public QueryFieldTree(Client client, Type type) {
        this.client = client;
        setTreeData(tree);
        setCheckmarksEnabled(true);

        QueryFieldTreeNodeRenderer renderer = new QueryFieldTreeNodeRenderer();
        setNodeRenderer(renderer);

        QueryFieldTreeNodeListener listener = new QueryFieldTreeNodeListener();
        getTreeViewNodeStateListeners().add(listener);
        getComponentKeyListeners().add(listener);

        type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> add(tree, f));
    }

    private void add(TreeBranch tree, Field field) {
        Type type = client.getType(field.getType().getName());
        if (type != null && "OBJECT".equals(type.getKind())) {
            TreeBranch branch = new TreeBranch(field.getName());
            branch.setUserData(new QueryFieldTreeEntry(field));
            tree.add(branch);
            return;
        }
        TreeNode node = new TreeNode(field.getName());
        node.setUserData(new QueryFieldTreeEntry(field));
        tree.add(node);
    }

    public void loadFieldsOnce(TreeBranch branch, Field field) {
        if (branch.isEmpty()) {
            client.getType(field.getType().getName()).getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(f -> add(branch, f));
        }
    }

}
