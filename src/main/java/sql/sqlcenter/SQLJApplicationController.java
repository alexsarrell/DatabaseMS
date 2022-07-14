package sql.sqlcenter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.DoublePredicate;

public class SQLJApplicationController implements iObservable{
    private java.util.LinkedList<iObserver> observers = new LinkedList<>();
    private Stage stage;
    private ArrayList<String> textField;
   private Double sceneX, sceneY;
    private int border = 4;
    @FXML
    TabPane tabPane;
    @FXML
    Menu schemasMenu;
    @FXML
    public void headerDrag(javafx.scene.input.MouseEvent me) {
        if (stage == null) stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        if((me.getSceneX() > border && me.getSceneY() > border) ||
                (me.getSceneX() - stage.getWidth() > border && me.getSceneY() > border)){
            Cursor cursor = stage.getScene().getCursor();
            if(!cursor.equals(Cursor.E_RESIZE) && !cursor.equals(Cursor.NE_RESIZE) && !cursor.equals(Cursor.NW_RESIZE)
                    && !cursor.equals(Cursor.SW_RESIZE) && !cursor.equals(Cursor.SE_RESIZE)
                    && !cursor.equals(Cursor.W_RESIZE) && !cursor.equals(Cursor.N_RESIZE)
                    && !cursor.equals(Cursor.S_RESIZE)){
                if(sceneX != null && sceneY != null){
                    stage.setX(me.getScreenX() - sceneX);
                    stage.setY(me.getScreenY() - sceneY);
                }
            }
        }
    }
    @FXML
    public void headerPressed(MouseEvent me) {
        if(stage != null){
            if((me.getSceneX() > border && me.getSceneY() > border) ||
                    (me.getSceneX() - stage.getWidth() > border && me.getSceneY() > border)) {
                sceneX = me.getSceneX();
                sceneY = me.getSceneY();
            }
        }
    }
    @FXML
    public void onCloseButton() {
        Platform.exit();
    }
    @FXML
    public void onIconified(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void onExecuteClick() {
        textField = new ArrayList<>(Arrays.asList(((TextArea)((BorderPane) tabPane
                .getSelectionModel().getSelectedItem()
                .getContent()).getRight()).getText().split("\n")));
        for(String s : textField) notifyObservers(s);
    }
    @Override
    public void addObserver(iObserver observer){
        if(!observers.contains(observer)) observers.add(observer);
    }
    @Override
    public void deleteObserver(iObserver observer){
        if(observers.contains(observer)) observers.remove(observer);
    }
    @Override
    public void notifyObservers(String query) {
        for(iObserver observer : observers){
            observer.update(query);
        }
    }
}
