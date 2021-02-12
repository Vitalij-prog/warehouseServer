package jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    
    public Connection getConnection() {

        Properties properties = new Properties();
        FileInputStream fis = null;

       /* try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            properties.load(in);
        } catch (IOException e) {

            System.out.println("getPropertiesException: " + e);
        }*/
        try {
            fis = new FileInputStream("./src/jdbc/db.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("db.url");
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");

        try {
            Class.forName(driver);
            //System.out.println("successful connection");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,login,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
