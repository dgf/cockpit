package org.aplatanao.cockpit;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.aplatanao.dimpl.Context;

public class CockpitApplication implements Application {

    private Window window;

    public void startup(Display display, Map<String, String> properties) throws Exception {
        window = new Context().get(CockpitWindow.class);
        window.setWidth(400);
        window.setHeight(300);
        window.open(display);
    }

    public boolean shutdown(boolean optional) throws Exception {
        if (window != null) {
            window.close();
        }
        return false;
    }

    public void suspend() throws Exception {
    }

    public void resume() throws Exception {
    }

    public static void main(String[] args) {
        DesktopApplicationContext.applyStylesheet("/styles.json");
        DesktopApplicationContext.main(CockpitApplication.class, args);
    }

}
