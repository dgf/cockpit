package org.aplatanao.cockpit.content.query;

import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.util.Comparator;
import java.util.stream.Collectors;

public class FieldTree extends FieldBranch {

    private TreeBranch tree = new TreeBranch();

    public FieldTree(Field field, Type type) {
        super(field);
        setStyleName("field-tree");
        setVisible(true);

        tree.setUserData(this);
        for (Field f : type.getFields().stream()
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList())) {
            TreeBranch branch = new TreeBranch(f.getName());
            branch.setUserData(addField(f));
            tree.add(branch);
        }
    }

    public void add(Sequence.Tree.Path path, Field subField) {
        TreeBranch parentBranch = (TreeBranch) Tree.get(tree, path);
        FieldBranch parentField = (FieldBranch) parentBranch.getUserData();

        TreeBranch subBranch = new TreeBranch(subField.getName());
        subBranch.setUserData(parentField.addField(subField));
        parentBranch.add(subBranch);
    }

    public void show(Tree.Path path, boolean visible) {
        TreeNode branch = Sequence.Tree.get(tree, path);
        ((FieldBranch) branch.getUserData()).setVisible(visible);
    }

    // enclose the query parent with curly brackets
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
