package sql.sqlcenter
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.io.IOException
import java.sql.*

class MyJDBC{
    private var url: String = ""
    private var currentTable: String = ""
    private var username: String = ""
    private var password: String = ""
    private var currentSchema: String = ""
    fun setCurrentTable(table: String){
        currentTable = table
    }
    fun setCurrentDatabase(schema: String){
        currentSchema = schema
    }
    @Throws(IOException::class)
    constructor(username: String, password: String, url: String){
        val connection: Connection
        this.url = url
        try{
            connection = DriverManager.getConnection(url,
                username, password)
            var resultSet = connection.metaData.catalogs
            this.username = username
            this.password = password
        }catch (exception: SQLException){
            throw IOException()
            exception.errorCode
        }
    }
    fun getAttributes(cName: String, tName: String) : ArrayList<String>{
        val connection: Connection
        val attributes: ArrayList<String> = ArrayList()
        try{
            connection = DriverManager.getConnection(url,
            username, password)
            val result: ResultSet = connection.metaData
                .getColumns(cName, null, tName, null)
            while(result.next()){
                attributes.add(result.getString("COLUMN_NAME"))
            }
        }catch (ex: SQLException){
            ex.printStackTrace()
        }
        return attributes
    }
    fun getTuples(cName: String, tName: String) : ObservableList<ObservableList<String>> {
        val connection: Connection
        val statement: Statement
        val tuples: ObservableList<ObservableList<String>> = FXCollections.observableArrayList()
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/$cName",
            username, password)
            statement = connection.createStatement()
            val rsc: ResultSet = connection.metaData
                .getColumns(cName, null, tName, null)
            var rsq: ResultSet = statement.executeQuery("SELECT * FROM $tName")
            while(rsc.next())
            {
                var tuple: ObservableList<String> = FXCollections.observableArrayList()
                while (rsq.next()){
                    tuple.add(rsq.getString(rsc.getString("COLUMN_NAME")))
                }
                rsq = statement.executeQuery("SELECT * FROM $tName")
                tuples.add(tuple)
            }
        }catch (ex: SQLException){
            ex.printStackTrace()
        }
        return tuples
    }
    fun getTables(cName: String) : ArrayList<String>{
        val connection: Connection
        val tables: ArrayList<String> = ArrayList()
        try{
            connection = DriverManager.getConnection(url,
                username, password)
            val resultSet = connection.metaData.getTables(cName,
                null, "%", arrayOf("TABLE"))
            while(resultSet.next()){
                tables.add(resultSet.getString(3))
            }
        }catch (exception: SQLException){
            exception.errorCode
        }
        return tables
    }
    fun getSchemas() : ArrayList<String>{
        val connection: Connection
        val schemas: ArrayList<String> = ArrayList()

        try{
            connection = DriverManager.getConnection(url,
                username, password)
            var resultSet = connection.metaData.catalogs
            while(resultSet.next()){
                schemas.add(resultSet.getString("TABLE_CAT"))
            }
        }catch (exception: SQLException){
            exception.errorCode
        }
        return schemas
    }
    @kotlin.jvm.Throws(SQLSyntaxErrorException::class)
    fun addTuple(values: String){
        val connection: Connection
        val statement: Statement
        try{
            connection = DriverManager.getConnection("$url/$currentSchema"
                , username, password)
            statement = connection.createStatement()
            println("current table: $currentTable, to: $currentSchema values: $values")
            statement.executeUpdate("INSERT INTO $currentTable VALUES ($values)")
        }catch (ex: SQLException){
            throw SQLSyntaxErrorException()
        }
    }
}