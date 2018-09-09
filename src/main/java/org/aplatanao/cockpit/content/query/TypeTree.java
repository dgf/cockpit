package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.OfType;
import org.aplatanao.graphql.Type;

import java.util.Comparator;
import java.util.stream.Collectors;

public class TypeTree extends TreeView {

    private TreeBranch tree = new TreeBranch();

    private FieldTree form;

    private Client client;

    public TypeTree(TypeRenderer renderer, TypeListener listener, FieldTree form, Client client, Type type) {
        this.form = form;
        this.client = client;

        setTreeData(tree);
        setCheckmarksEnabled(true);
        setShowMixedCheckmarkState(true);
        setNodeRenderer(renderer);

        getComponentKeyListeners().add(listener);
        getTreeViewNodeStateListeners().add(listener);
        getTreeViewBranchListeners().add(listener);
        getTreeViewSelectionListeners().add(listener);

        for (Field field : type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList())) {
            tree.add(subNode(field));
        }
    }

    private TreeNode subNode(Field field) {
        boolean isList = false;
        TreeNode node;

        Type type = client.getType(field.getType().getName());
        if (type == null) {
            OfType ofType = field.getType().getOfType();
            if (ofType != null) {
                if ("LIST".equals(ofType.getKind())) {
                    if ("OBJECT".equals(ofType.getOfType().getKind())) {
                        isList = true;
                        type = client.getType(ofType.getOfType().getName());
                    }
                } else {
                    type = client.getType(ofType.getName());
                }
            }
        }

        if (type == null) {
            throw new IllegalArgumentException("invalid " + field);
        }

        String name;
        if (isList) {
            name = field.getName() + " [" + type.getName() + "]";
        } else {
            name = field.getName() + " (" + type.getName() + ")";
        }

        if ("OBJECT".equals(type.getKind())) {
            node = new TreeBranch(name);
        } else {
            node = new TreeNode(name);
        }
        node.setUserData(type);
        return node;
    }


    public void loadSubBranchOnce(Sequence.Tree.Path path) {
        TreeBranch branch = (TreeBranch) Sequence.Tree.get(tree, path);
        if (branch.isEmpty()) {
            Type type = (Type) branch.getUserData();
            for (Field field : client.getType(type.getName()).getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList())) {
                branch.add(subNode(field));
                form.add(path, field);
            }
        }
    }

    public void check(Sequence.Tree.Path path) {
        form.show(path, this.getNodeCheckState(path) != NodeCheckState.UNCHECKED);
    }
}
