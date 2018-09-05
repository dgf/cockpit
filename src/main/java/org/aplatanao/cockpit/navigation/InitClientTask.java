package org.aplatanao.cockpit.navigation;

import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.aplatanao.graphql.Client;

public class InitClientTask extends Task<Boolean> {

    private Client client;

    public InitClientTask(Client client) {
        this.client = client;
    }

    @Override
    public Boolean execute() throws TaskExecutionException {
        client.init();
        return client.isInitialized();
    }
}
