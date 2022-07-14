package sql.sqlcenter;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ErrorMessage implements Message {
    @Override
    public void showMessage(String message) {
        Message.show(message);
    }
    public static void showErrorMessage(){

    }
}
