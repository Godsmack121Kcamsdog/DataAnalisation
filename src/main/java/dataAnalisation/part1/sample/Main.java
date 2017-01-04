package dataAnalisation.part1.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL path = getClass().getResource("/main.fxml");
        Parent parent = FXMLLoader.load(path);
        stage.setScene(new Scene(parent));
        stage.setTitle("Аналіз даних");
        stage.setMaximized(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Double round(double data, int scale){
        return new BigDecimal(data).setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }
}
