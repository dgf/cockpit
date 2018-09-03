package org.aplatanao.cockpit;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.aplatanao.context.Context;

public class CockpitApplication implements Application {

    private Window window;

    private Context context = new Context();

    public void startup(Display display, Map<String, String> properties) throws Exception {
        window = context.get(CockpitWindow.class);
        window.open(display);
        window.setWidth(400);
        window.setHeight(300);
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
