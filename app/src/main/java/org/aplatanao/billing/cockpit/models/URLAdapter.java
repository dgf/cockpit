package org.aplatanao.billing.cockpit.models;

import com.dooapp.fxform.adapter.Adapter;
import com.dooapp.fxform.adapter.AdapterException;

import java.net.MalformedURLException;
import java.net.URL;

public class URLAdapter implements Adapter<URL, String> {

    @Override
    public String adaptTo(URL url) throws AdapterException {
        if (url == null) {
            return "";
        }
        return url.toString();
    }

    @Override
    public URL adaptFrom(String s) throws AdapterException {
        if (s == null || s.isEmpty()) {
            return null;
        }
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new AdapterException(e);
        }
    }
}