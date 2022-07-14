module sql.sqlcenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kotlin.stdlib;


    opens sql.sqlcenter to javafx.fxml;
    exports sql.sqlcenter;
}