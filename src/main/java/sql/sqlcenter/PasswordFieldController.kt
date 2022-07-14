package sql.sqlcenter

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.util.*

class PasswordFieldController{
    @FXML
    var labelMessage: Label = Label()
    @FXML
    var url: TextField = TextField()
    @FXML
    var userLogin: TextField = TextField()
    @FXML
    var userPassword: TextField = TextField()
    @FXML
    fun onCloseButton(){
        Platform.exit()
    }
    @FXML
    fun onEnterButton(ae: ActionEvent){
        val sLogin = userLogin.text
        val url = url.text
        val password = userPassword.text
        if(sLogin.trim(' ').isNotEmpty()
            && password.trim(' ').isNotEmpty()){
            if(password.length >= 40 || !Login.enter(sLogin, password, url)){
                labelMessage.text = "incorrect url, username or password"
            }
            else{
                MainPage().also {
                    it.showWindow((ae.source as Node).scene.window as Stage)
                }
            }
        }
    }

}