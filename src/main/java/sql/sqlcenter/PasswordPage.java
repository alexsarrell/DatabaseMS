package sql.sqlcenter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PasswordPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //ResizeHelper.addResizeListener(primaryStage);
        FXMLLoader fxmlPasswordLoader = new FXMLLoader(
                PasswordPage.class.getResource("passwordField.fxml"));
        try{
            Scene scene = new Scene(fxmlPasswordLoader.load(), 300, 240);
            scene.getStylesheets().add(
                    PasswordPage.class.getResource("MainSQLWindowCSS.css")
                            .toExternalForm());
            primaryStage.setScene(scene);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent key) ->{
                if(key.getCode().equals(KeyCode.ENTER)){
                    Button bth = ((Button) fxmlPasswordLoader
                            .getNamespace().get("enterButton"));
                    bth.fire();
                }
            });
            primaryStage.show();
        }catch (java.io.IOException ex){
            ex.printStackTrace();
        }

    }
}
