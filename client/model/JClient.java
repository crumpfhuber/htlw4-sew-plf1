package client.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JClient extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/gui.fxml"));
        stage.setTitle("SocketChat");
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.show();
    }
}
