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

public class QueryFieldTree extends TreeView {

    private TreeBranch tree = new TreeBranch();

    private QueryForm form;

    private Client client;

    public QueryFieldTree(QueryForm form, Client client, Field field) {
        this.form = form;
        this.client = client;

        setTreeData(tree);
        setCheckmarksEnabled(true);
        setShowMixedCheckmarkState(true);
        setNodeRenderer(new QueryFieldTreeNodeRenderer());
        QueryFieldTreeNodeListener listener = new QueryFieldTreeNodeListener();
        getTreeViewNodeStateListeners().add(listener);
        getTreeViewBranchListeners().add(listener);

        Type type = client.getType(field.getType().getName());
        type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> add(tree, f));
    }

    private void add(TreeBranch tree, Field field) {
        Type type = client.getType(field.getType().getName());
        if (type != null) {
            if ("OBJECT".equals(type.getKind())) {
                addBranch(tree, field.getName() + " (" + type.getName() + ")", type);
                return;
            }
            if ("SCALAR".equals(type.getKind()) || "ENUM".equals(type.getKind())) {
                Type subType = client.getType(type.getName());
                addNode(tree, subType, field.getName() + " (" + type.getName() + ")");
                return;
            }
            throw new IllegalArgumentException("invalid type " + type);
        }
        OfType ofType = field.getType().getOfType();
        if (ofType != null) {
            if ("LIST".equals(ofType.getKind())) {
                if ("OBJECT".equals(ofType.getOfType().getKind())) {
                    String name = ofType.getOfType().getName();
                    addBranch(tree, field.getName() + " [" + name + "]", client.getType(name));
                    return;
                }
                throw new IllegalArgumentException("invalid ofType LIST " + ofType);
            }
            if ("OBJECT".equals(ofType.getKind())) {
                addBranch(tree, field.getName() + " (" + ofType.getName() + ")", client.getType(ofType.getName()));
                return;
            }
            if ("SCALAR".equals(ofType.getKind()) || "ENUM".equals(ofType.getKind())) {
                Type subType = client.getType(ofType.getName());
                addNode(tree, subType, field.getName() + " (" + ofType.getName() + ")");
                return;
            }
            throw new IllegalArgumentException("invalid ofType " + ofType);
        }
        throw new IllegalArgumentException("invalid field without type or ofType: " + field);
    }

    private void addNode(TreeBranch tree, Type scalarType, String text) {
        TreeNode node = new TreeNode(text);
        node.setUserData(scalarType);
        tree.add(node);
    }

    private void addBranch(TreeBranch tree, String name, Type type) {
        TreeBranch branch = new TreeBranch(name);
        branch.setUserData(type);
        tree.add(branch);
    }

    public void loadSubBranchOnce(Sequence.Tree.Path path) {
        TreeBranch branch = (TreeBranch) Sequence.Tree.get(tree, path);
        if (branch.isEmpty()) {
            Type type = (Type) branch.getUserData();

            client.getType(type.getName()).getFields().stream()
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
        System.out.println("check: " + path);
    }

}
