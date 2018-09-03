package org.aplatanao.cockpit.tree;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.API;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Query;
import org.aplatanao.graphql.Type;

import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

public class CockpitTree extends TreeView {

    private List<Object> tree = new ArrayList<>();

    public CockpitTree(TreeNodeRenderer renderer, TreeNodeListener listener) {
        setStyleName("tree");
        setNodeRenderer(renderer);
        getTreeViewSelectionListeners().add(listener);
        getComponentKeyListeners().add(listener);

        try {
            addAPI(new API("EtMDB", "https://etmdb.com/graphql", "Ethiopian Movie Database"));
            addAPI(new API("gdom", "http://gdom.graphene-python.org/graphql", "DOM Queries"));
            addAPI(new API("melody", "https://api.melody.sh/graphql", "Golang packages"));
            addAPI(new API("local", "http://localhost:8080/graphql", "awesome local endpoint"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setTreeData(tree);
    }

    public void addAPI(API api) throws URISyntaxException {
        Client client = new Client(api);

        TreeBranch branch = new TreeBranch(api.name);
        branch.setUserData(client);
        tree.add(branch);

        CompletableFuture.runAsync(client::init).thenRun(() -> {
            TreeBranch types = new TreeBranch("types");
            branch.add(types);
            client.getTypes().stream()
                    .sorted(Comparator.comparing(Type::getName))
                    .forEach(t -> {
                        TreeNode type = new TreeNode(t.getName());
                        type.setUserData(t);
                        types.add(type);
                    });

            TreeBranch queries = new TreeBranch("queries");
            branch.add(queries);
            client.getQueries().stream()
                    .sorted(Comparator.comparing(Query::getName))
                    .forEach(q -> {
                        TreeNode query = new TreeNode(q.getName());
                        query.setUserData(q);
                        queries.add(query);
                    });
        });
    }

}
