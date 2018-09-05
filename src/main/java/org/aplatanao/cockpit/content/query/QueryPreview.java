package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.Expander;
import org.apache.pivot.wtk.TextArea;

public class QueryPreview extends Expander {

    public QueryPreview() {
        setTitle("Preview");
        setStyleName("preview");

        TextArea preview = new TextArea();
        preview.setText("foo\nbar");

        setContent(preview);
    }
}
