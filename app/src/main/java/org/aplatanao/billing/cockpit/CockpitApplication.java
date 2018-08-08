package org.aplatanao.billing.cockpit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.aplatanao.billing.cockpit.control.Window;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CockpitApplication extends Application {

    private ConfigurableApplicationContext context;

    private Window window;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CockpitApplication.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
        window = context.getBean(Window.class);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplatanao Cockpit");
        primaryStage.setScene(new Scene(window, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }
}
