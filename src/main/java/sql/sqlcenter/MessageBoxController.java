package sql.sqlcenter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageBoxController {
    @FXML
    private Label messageLabel;
    private String message;
    public MessageBoxController(String message){
        this.message = message;
    }
    public void initialize(){
        messageLabel.setText(message);
    }
}
