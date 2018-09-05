package org.aplatanao.cockpit.overview;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.TextInput;

public class QueryForm extends Form {

    public QueryForm() {
        setName("query");

        Form.Section section = new Form.Section();
        section.setHeading("Query");
        getSections().add(section);

        TextInput name = new TextInput();
        name.setTextKey("name");
        Form.setLabel(name, "Name");
        section.add(name);

        TextInput type = new TextInput();
        type.setTextKey("type.name");
        Form.setLabel(type, "Type");
        section.add(type);

        TextInput desc = new TextInput();
        desc.setTextKey("description");
        Form.setLabel(desc, "Description");
        section.add(desc);

    }
}
