package sql.sqlcenter;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import kotlin.TypeCastException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainPage extends Application {
    static MyJDBC dbSController;
    @Override
    public void start(Stage primaryStage) {
        try{
            dbSController = Login.getSQLController();
        }catch (IOException e){
            throw new RuntimeException("controller is not initialized");
        }
        FXMLLoader fxmlMainLoader = new FXMLLoader(
                MainPage.class.getResource("SQLApplication.fxml"));
        try {
            Scene scene = new Scene(fxmlMainLoader.load(), 600, 400);
            try{
                scene.getStylesheets().add(
                        Objects.requireNonNull
                                        (PasswordPage.class
                                                .getResource("MainSQLWindowCSS.css"))
                                .toExternalForm());
            }catch (NullPointerException ex){
                ex.printStackTrace();
            }
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Menu schemasMenu;

        if(dbSController != null){
            ActionListener al = new ActionListener(dbSController);
            SQLJApplicationController controller = fxmlMainLoader.getController();
            controller.addObserver(al);
            ArrayList<String> schemas = dbSController.getSchemas();
            schemasMenu = (Menu)fxmlMainLoader.getNamespace().get("schemasMenu");
            for(String i : schemas){
                Menu menu = new Menu(i);
                menu.setOnAction(actionEvent -> {
                    dbSController.setCurrentDatabase(menu.getText());
                });
                schemasMenu.getItems().add(menu);
                ArrayList<String> tables = dbSController.getTables(i);
                for(String j : tables) {
                    MenuItem item = new MenuItem(j);
                    item.setOnAction(actionEvent -> {
                        dbSController.setCurrentTable(item.getText());
                        ArrayList<String> columns =
                                dbSController.getAttributes(item.getParentMenu().getText(),
                                item.getText());
                        ObservableList<ObservableList<String>> tuples =
                                dbSController.getTuples(item.getParentMenu().getText(),
                                        item.getText());

                        try{
                            ListView<String>
                                    getListView =
                                    (ListView<String>)fxmlMainLoader
                                    .getNamespace().get("getTableView");
                                    TableView addPane =
                                            (TableView) fxmlMainLoader
                                            .getNamespace().get("addPane"),
                                    deletePane =
                                            (TableView)fxmlMainLoader
                                            .getNamespace().get("deletePane");
                            addPane.getItems().clear();
                            deletePane.getItems().clear();
                            addPane.getColumns().clear();
                            deletePane.getColumns().clear();
                            getListView.getItems().clear();
                            int max = -1;
                            for(ObservableList<String> m : tuples){
                                if(m.size() > max) max = m.size();
                            }
                            for(int m = 0; m < max; m++){
                                addPane.getItems().add(m);
                                deletePane.getItems().add(m);
                            }
                            for(int k = 0; k < columns.size(); k++){
                                getListView.getItems().add(columns.get(k));
                                TableColumn<Integer, String> currentColumn =
                                        new TableColumn<>(columns.get(k));
                                ObservableList<String> line = tuples.get(k);
                                currentColumn.setCellValueFactory(cellData -> {
                                    Integer rowIndex = Integer.parseInt(cellData.getValue().toString());
                                    return new ReadOnlyStringWrapper
                                            (line.get(rowIndex));
                                });
                                addPane.getColumns().add(currentColumn);
                                deletePane.getColumns().add(currentColumn);
                            }
                        }catch (TypeCastException e){
                            e.printStackTrace();
                        }
                    });
                    menu.getItems().add(item);
                }
            }
        }
    }
    public void showWindow(Stage stage){
        start(stage);
        ResizeHelper.addResizeListener(stage);
    }
}
