package sql.sqlcenter;

import java.io.IOException;

public class Login {
    static MyJDBC sqlController;
    public static MyJDBC getSQLController() throws IOException{
        return sqlController;
    }
    public static boolean enter(String login, String password, String url){
        try{
            sqlController = new MyJDBC(login, password, url);
        }catch (IOException e){
            return false;
        }
        return true;
    }
}
