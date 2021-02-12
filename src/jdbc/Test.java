package jdbc;

import java.sql.*;

public class Test {
    public static void main(String[] args) {

        Util util = new Util();
        Connection connection = util.getConnection();
        if(connection != null) {
            System.out.println("success");      //connection ok!
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("fail");
        }
    }
}
