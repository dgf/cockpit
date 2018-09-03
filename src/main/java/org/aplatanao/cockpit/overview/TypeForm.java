package org.aplatanao.cockpit.overview;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.TextInput;

public class TypeForm extends Form {

    public TypeForm() {
        setName("type");

        Form.Section section = new Form.Section();
        section.setHeading("Type");
        getSections().add(section);

        TextInput name = new TextInput();
        name.setTextKey("name");
        Form.setLabel(name, "Name");
        section.add(name);

        TextInput kind = new TextInput();
        kind.setTextKey("kind");
        Form.setLabel(kind, "Kind");
        section.add(kind);

        TextInput apiDesc = new TextInput();
        apiDesc.setTextKey("description");
        Form.setLabel(apiDesc, "Description");
        section.add(apiDesc);
    }
}
