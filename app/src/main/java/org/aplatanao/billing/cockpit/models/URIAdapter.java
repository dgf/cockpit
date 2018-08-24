package org.aplatanao.billing.cockpit.models;

import com.dooapp.fxform.adapter.Adapter;
import com.dooapp.fxform.adapter.AdapterException;

import java.net.URI;
import java.net.URISyntaxException;

public class URIAdapter implements Adapter<URI, String> {

    @Override
    public String adaptTo(URI uri) throws AdapterException {
        if (uri == null) {
            return "";
        }
        return uri.toString();
    }

    @Override
    public URI adaptFrom(String s) throws AdapterException {
        if (s == null || s.isEmpty()) {
            return null;
        }
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            throw new AdapterException(e);
        }
    }
}