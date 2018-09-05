package org.aplatanao.cockpit.navigation;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.aplatanao.graphql.API;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class CockpitNavigation extends TreeView {

    private List<Object> treeData = new ArrayList<>();

    public CockpitNavigation(NavigationRenderer renderer, NavigationListener listener) {
        setStyleName("navigation");
        setNodeRenderer(renderer);
        getTreeViewSelectionListeners().add(listener);
        getComponentKeyListeners().add(listener);
        getComponentMouseButtonListeners().add(listener);
        setTreeData(treeData);

        try {
            addAPI(new API("EtMDB", "https://etmdb.com/graphql", "Ethiopian Movie Database"));
            addAPI(new API("gdom", "http://gdom.graphene-python.org/graphql", "DOM Queries"));
            addAPI(new API("melody", "https://api.melody.sh/graphql", "Golang packages"));
            addAPI(new API("local", "http://localhost:8080/graphql", "awesome local endpoint"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void addAPI(API api) throws URISyntaxException {
        Client client = new Client(api);

        TreeNode preview = new TreeNode(api.name);
        preview.setUserData(client);
        int pos = treeData.add(preview);

        TreeBranch branch = new TreeBranch(api.name);
        branch.setUserData(client);

        new InitClientTask(client).execute(new TaskAdapter<>(new TaskListener<Boolean>() {
            @Override
            public void taskExecuted(Task<Boolean> task) {
                if (task.getResult()) {
                    TreeBranch types = new TreeBranch("types");
                    for (Type type : client.getTypes().stream()
                            .sorted(String::compareTo)
                            .map(client::getType)
                            .collect(Collectors.toList())) {
                        TreeNode node = new TreeNode(type.getName());
                        node.setUserData(type);
                        types.add(node);
                    }
                    branch.add(types);

                    TreeBranch queries = new TreeBranch("queries");
                    for (Field query : client.getQueries()) {
                        TreeNode node = new TreeNode(query.getName());
                        node.setUserData(query);
                        queries.add(node);
                    }
                    branch.add(queries);
                    treeData.update(pos, branch);
                }
            }

            @Override
            public void executeFailed(Task<Boolean> task) {
                // report error?
            }
        }));
    }

}
