package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;

public class SubField extends BoxPane {

    private RequestField requestField;

    private Checkbox checkbox = new Checkbox();

    private PushButton action = new PushButton();

    private ButtonPressListener listener;

    public SubField(Client client, Field subField) {
        setOrientation(Orientation.HORIZONTAL);

        requestField = new RequestField(client, subField);
        checkbox.getButtonPressListeners().add(e -> requestField.toggle());

        add(checkbox);
        add(action);
        add(requestField);
    }

    public RequestField getRequestField() {
        return requestField;
    }

    public boolean isCheckedSelected() {
        return checkbox.isSelected();
    }

    public void setCheckedEnabled(boolean enabled) {
        checkbox.setEnabled(enabled);
    }

    public void setAction(String sign, ButtonPressListener listener) {
        action.setButtonData(sign);
        if (this.listener != null) {
            action.getButtonPressListeners().remove(listener);
        }
        this.listener = listener;
        action.getButtonPressListeners().add(listener);
    }

}
