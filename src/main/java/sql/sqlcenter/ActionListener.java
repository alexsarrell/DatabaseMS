package sql.sqlcenter;

import kotlin.TypeCastException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Iterator;

public class ActionListener implements iObserver{
    private MyJDBC dbConnection;
    public ActionListener(MyJDBC dbConnection){
        this.dbConnection = dbConnection;
    }
    private void normalizeQuery(String query){
        String finalQuery = "";
        for(int i = 0; i < query.length(); i++){

        }
    }
    @Override
    public void update(String data){
        if(data.length() != 0 && !data.trim().equals("")){
            normalizeQuery(data);
            try{
                dbConnection.addTuple(data);
            }catch(SQLSyntaxErrorException ex){
                Message.show("Incorrect data entered,\nplease check your query syntax");
            }
        }
    }
}
