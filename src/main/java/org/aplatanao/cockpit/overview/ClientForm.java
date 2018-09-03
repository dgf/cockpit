package org.aplatanao.cockpit.overview;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.TextInput;

public class ClientForm extends Form {

    public ClientForm() {
        setName("client");

        Form.Section section = new Form.Section();
        section.setHeading("API");
        getSections().add(section);

        TextInput name = new TextInput();
        name.setTextKey("api.name");
        Form.setLabel(name, "Name");
        section.add(name);

        TextInput apiURI = new TextInput();
        apiURI.setTextKey("api.uri");
        Form.setLabel(apiURI, "URI");
        section.add(apiURI);

        TextInput apiDesc = new TextInput();
        apiDesc.setTextKey("api.description");
        Form.setLabel(apiDesc, "Description");
        section.add(apiDesc);
    }
}
