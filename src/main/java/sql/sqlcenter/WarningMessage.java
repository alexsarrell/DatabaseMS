package sql.sqlcenter;

public class WarningMessage implements Message{
    @Override
    public void showMessage(String message){
        Message.show(message);
    }
    public static void showWarningMessage(String message){
        
    }
}
