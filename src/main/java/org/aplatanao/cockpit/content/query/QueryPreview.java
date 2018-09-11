package org.aplatanao.cockpit.content.query;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.*;
import org.aplatanao.cockpit.details.CockpitDetails;
import org.aplatanao.cockpit.tasks.ExecuteClientTask;
import org.aplatanao.graphql.Client;

import java.io.IOException;

public class QueryPreview extends TablePane {

    public QueryPreview(Client client, FieldTree tree, CockpitDetails details) {
        setStyleName("preview");

        TextArea preview = new TextArea();
        preview.setText(tree.toString());

        BoxPane buttons = new BoxPane(Orientation.VERTICAL);

        PushButton refreshButton = new PushButton("Refresh");
        refreshButton.getButtonPressListeners().add(b -> preview.setText(tree.toString()));
        buttons.add(refreshButton);

        PushButton requestButton = new PushButton("Request");
        requestButton.getButtonPressListeners().add(b -> {
                    new ExecuteClientTask(client, preview.getText()).execute(new TaskAdapter<>(new TaskListener<ObjectNode>() {
                        @Override
                        public void taskExecuted(Task<ObjectNode> task) {
                            try {
                                details.response(task.getResult());
                            } catch (IOException e) {
                                throw new RuntimeException("show response details failed", e);
                            }
                        }

                        @Override
                        public void executeFailed(Task<ObjectNode> task) {
                            throw new IllegalArgumentException(client.getStatus());
                        }
                    }));
                }
        );
        buttons.add(requestButton);

        TablePane.Column stretch = new TablePane.Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        TablePane.Column right = new TablePane.Column();
        right.setWidth(-1);
        getColumns().add(right);

        Row mainRow = new Row();
        mainRow.setHeight(-1);
        getRows().add(mainRow);
        mainRow.add(preview);
        mainRow.add(buttons);
    }
}
