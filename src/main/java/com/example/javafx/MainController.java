package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button btn;
    private int count = 0;
    @FXML
    public void initialize() {
        btn.setText(String.valueOf("click"));
    }

    @FXML
    private void click(ActionEvent event) {

        btn.setText(String.valueOf(count++));
    }
}