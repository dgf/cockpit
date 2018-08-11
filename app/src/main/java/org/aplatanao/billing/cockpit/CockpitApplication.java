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

    private static String[] args;

    @Override
    public void start(Stage primaryStage) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CockpitApplication.class);
        context = builder.run(getParameters().getRaw().toArray(args));
        Window window = context.getBean(Window.class);

        Scene scene = new Scene(window, 800, 600);
        scene.getStylesheets().add("cockpit.css");
        primaryStage.setTitle("Aplatanao Cockpit");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        CockpitApplication.args = args;
        launch(CockpitApplication.class);
    }
}
