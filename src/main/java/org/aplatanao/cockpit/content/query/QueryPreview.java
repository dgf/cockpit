package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.TextArea;

public class QueryPreview extends Border {

    public QueryPreview() {
        setTitle("Preview");
        TextArea preview = new TextArea();
        preview.setText("foo\nbar");
        setContent(preview);
    }
}
