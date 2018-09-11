package org.aplatanao.cockpit.tasks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.aplatanao.graphql.Client;

import java.io.IOException;

public class ExecuteClientTask extends Task<ObjectNode> {

    private Client client;

    private String query;

    public ExecuteClientTask(Client client, String query) {
        this.client = client;
        this.query = query;
    }

    @Override
    public ObjectNode execute() throws TaskExecutionException {
        try {
            return client.execute(query);
        } catch (IOException e) {
            throw new TaskExecutionException(e);
        }
    }
}

