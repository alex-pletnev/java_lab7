package SQLutil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInitiator {
    private final Connection connection;

    public SQLInitiator(Connection connection) {
        this.connection = connection;
    }


    public void init() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Cities (" +
                        "id int primary key," +
                        "name varchar(36) not null check ( name <> '')," +
                        "x decimal check (x < 209)," +
                        "y decimal check(y <= 33)," +
                        "creationDate date not null," +
                        "area decimal check (area > 0)," +
                        "population int check (population > 0)," +
                        "metersAboveSeaLevel decimal," +
                        "timezone decimal check ( timezone > -13 and timezone < 15)," +
                        "capital bool," +
                        "government varchar(20) not null check ( government = 'ARISTOCRACY' or government = 'GERONTOCRACY' or government = 'DESPOTISM' or government = 'TIMOCRACY')," +
                        "governor varchar(36) not null," +
                        "username varchar(50) not null " +
                        ")"
        );
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "username varchar(50) PRIMARY KEY," +
                        "password bytea[] default(null)" +
                        ")"
        );
        statement.executeUpdate(
                "CREATE SEQUENCE IF NOT EXISTS sequence START 1"
        );

    }
}
