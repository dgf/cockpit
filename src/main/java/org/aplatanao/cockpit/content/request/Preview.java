package org.aplatanao.cockpit.content.request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.*;
import org.aplatanao.cockpit.details.CockpitDetails;
import org.aplatanao.cockpit.tasks.ExecuteClientTask;
import org.aplatanao.graphql.Client;

import java.io.IOException;

public class Preview extends TablePane {

    public Preview(Client client, Editor editor, CockpitDetails details) {
        setStyleName("request-preview");

        TextArea preview = new TextArea();
        preview.setText(editor.toString());

        BoxPane buttons = new BoxPane(Orientation.VERTICAL);

        PushButton refreshButton = new PushButton("Refresh");
        refreshButton.getButtonPressListeners().add(b -> preview.setText(editor.toString()));
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

        Column stretch = new Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        Column right = new Column();
        right.setWidth(-1);
        getColumns().add(right);

        Row mainRow = new Row();
        mainRow.setHeight(-1);
        getRows().add(mainRow);
        mainRow.add(preview);
        mainRow.add(buttons);
    }
}
