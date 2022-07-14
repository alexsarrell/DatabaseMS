package sql.sqlcenter

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.stage.Stage
import org.w3c.dom.events.MouseEvent

class `SQLApplicationController(leg)` {
    var xScenePosition = 0;
    var yScenePosition = 0;
    fun onIconified(ae: ActionEvent){
        var stage = (ae.source as Node).scene.window as Stage
        stage.isIconified = true
    }
    fun onCloseButton(){
        Platform.exit()
    }
    @FXML
    fun onToolMousePressed(mouseEvent: MouseEvent){
        xScenePosition = mouseEvent.clientX;
        yScenePosition = mouseEvent.clientY;
    }
    @FXML
    fun onToolMouseDragged(mouseEvent: MouseEvent, ae: ActionEvent){
        var stage = (ae.source as Node).scene.window as Stage
        stage.x = mouseEvent.screenX - xScenePosition as Double;
        stage.y = mouseEvent.screenY - yScenePosition as Double;
    }
}