package client.controller;

import client.model.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Worker worker;

    @FXML private TextArea chat;
    @FXML private TextField name;
    @FXML private TextField input;
    @FXML private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        worker = new Worker(); // create worker class
        new Thread(worker).start();

        chat.textProperty().bind(worker.getMessageProperty()); // bind prop from worker to text field
        button.cancelButtonProperty().bind(chat.textProperty().isEmpty());
    }

    @FXML
    void onButtonPressed(ActionEvent event) {
        worker.sendMessage(name.getText(), input.getText());
        input.setText("");
    }


}
