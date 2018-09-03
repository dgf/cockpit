package org.aplatanao.context.example;

import org.aplatanao.context.Context;

import java.util.HashMap;

public class Storage extends HashMap<String, Entry> {

    private Context context;

    public Storage(Context context) {
        super();
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}