package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.Comparator;

public class QueryFieldTree extends TreeView {

    public QueryFieldTree(Client client, Type type) {
        List<Object> tree = new ArrayList<>();
        setTreeData(tree);
        setCheckmarksEnabled(true);

        QueryFieldTreeNodeRenderer renderer = new QueryFieldTreeNodeRenderer();
        setNodeRenderer(renderer);

        QueryFieldTreeNodeListener listener = new QueryFieldTreeNodeListener();
        getTreeViewSelectionListeners().add(listener);
        getComponentKeyListeners().add(listener);

        type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> addField(tree, client, f));
    }

    private void addField(List<Object> tree, Client client, Field field) {
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
}
