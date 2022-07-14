package sql.sqlcenter;

import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;

public interface Message {
    void showMessage(String message);
    static void show(String message)
    {
        javafx.stage.Stage stage = new javafx.stage.Stage();
        //stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(ErrorMessage.class.getResource("messageBox.fxml"));
        MessageBoxController controller = new MessageBoxController(message);
        loader.setController(controller);
        try{
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
