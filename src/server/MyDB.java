package server;

import entities.Order;
import entities.Product;
import entities.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyDB {
    String url = "jdbc:mysql://localhost/shop?useUnicode=true&serverTimezone=UTC";
    String login = "root";
    String password = "root";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection connection;


    public MyDB()  throws ClassNotFoundException {
        Class.forName(driver);
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);

        }
    }



    public String searchUser(String login, String pass) throws SQLException {
        String query = "select * from users";
        //StringBuilder res = new StringBuilder();
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        String role = "";
        while(resSet.next()) {
            if(login.equals(resSet.getString(2)) && pass.equals(resSet.getString(3))) {
                System.out.println("authorization was successful");
                role = resSet.getString(4);
                flag = true;
                break;
            }
        }
        if(!flag) {
            System.out.println("wrong login or password");
        }
        resSet.close();
        st.close();
        return role;
    }

    public String identification(String login, String pass) throws SQLException {
        String query = "select * from users";
        //StringBuilder res = new StringBuilder();
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        String role = "";
        while(resSet.next()) {
            if(login.equals(resSet.getString(2)) && pass.equals(resSet.getString(3))) {
                System.out.println("authorization was successful");
                role = resSet.getString(4);
                flag = true;
                break;
            }
        }
        if(!flag) {
            System.out.println("wrong login or password");
        }
        resSet.close();
        st.close();
        return role;
    }

    public String addUser(String login, String password, String role) throws SQLException {
        String query = "select * from users";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        while(resSet.next()) {
            //res.append(resSet.getString(1)).append("\t").append(resSet.getString(2)).append("\n");
            if(login.equals(resSet.getString(2))) {
                //System.out.println("authorization was successful");
                flag = true;
                break;
            }
        }
        resSet.close();
        if(flag) {
            resSet.close();
            st.close();
            return "";
        }
        st = connection.prepareStatement("insert into users (user_name, user_password, role) values(?,?,?) ");
        st.setString(1, login);
        st.setString(2,password);
        st.setString(3, role);
        st.addBatch();
        st.executeUpdate();

        st.close();
        return "success";
    }

    public String addUser(User user) throws SQLException {
        String query = "select * from users";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        while(resSet.next()) {
            if(user.getUserName().equals(resSet.getString(2))) {

                flag = true;
                break;
            }
        }
        resSet.close();
        if(flag) {
            resSet.close();
            st.close();
            return "";
        }
        st = connection.prepareStatement("insert into users (user_name, user_password, role) values(?,?,?) ");
        st.setString(1, user.getUserName());
        st.setString(2, user.getPassword());
        st.setString(3, user.getRole());
        st.addBatch();
        st.executeBatch();

        st.close();
        return "success";
    }

    public ArrayList<User> getUsers() throws SQLException {
        String query = "select * from users";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        ArrayList<User> list = new ArrayList<>();

        while(resSet.next()) {
            list.add(new User(resSet.getInt(1), resSet.getString(2), resSet.getString(4)));
        }
        resSet.close();
        st.close();
        return list;

    }

    public String deleteUser(String id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("select * from users where id = ?");
        st.setInt(1, Integer.parseInt(id));
        st.addBatch();

        ResultSet resSet = st.executeQuery();
        if(resSet.next() && (resSet.getInt(1) != 1 && resSet.getInt(1) != 2)) {
            st = connection.prepareStatement("delete from users where id = ?");
            st.setInt(1, Integer.parseInt(id));
            st.addBatch();
            st.executeBatch();
        } else {
            return "";
        }
        return "success";
    }

    
    public ArrayList<Product> getProducts(String query) throws SQLException{
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        ArrayList<Product> list = new ArrayList<>();

        while(resSet.next()) {
           list.add(new Product(resSet.getInt(1), resSet.getString(2),  resSet.getInt(4),resSet.getDouble(3),0));
        }
        resSet.close();

        return list;

    }

    public ArrayList<Product> getProducts(String option, String data) throws SQLException {

        String searchQuery = "";
        switch(option) {
            case "product_name":
                searchQuery = " where product_name = ";
                break;
            case "product_price":
                searchQuery = " where product_price = ";
                break;
            case "amount":
                searchQuery = " where amount = ";
                break;

        }

        String query = "select * from products" + searchQuery + "?";

        PreparedStatement st = connection.prepareStatement(query);
        switch(option) {
            case "product_name":
                st.setString(1, data);
                break;
            case "product_price":
                st.setDouble(1, Double.parseDouble(data));
                break;
            case "amount":
                st.setInt(1, Integer.parseInt(data));
                break;
        }
        st.addBatch();
        ResultSet resSet = st.executeQuery();

        ArrayList<Product> list = new ArrayList<>();

        while(resSet.next()) {
            list.add(new Product(resSet.getInt(1), resSet.getString(2),  resSet.getInt(4),resSet.getDouble(3), 0));
        }
        resSet.close();

        return list;

    }

    public ArrayList<Order> getOrders() throws SQLException{

        String query = "select orders.id, users.user_name, products.product_name, orders.amount," +
                " orders.order_price, orders.order_date," +
                "   orders.order_time, orders.status" +
                "   from orders inner join users on orders.userId = users.id" +
                "   inner join products on orders.productId = products.id";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        ArrayList<Order> list = new ArrayList<>();

        while(resSet.next()) {
            list.add(new Order(resSet.getInt(1), resSet.getString(2),
                    resSet.getString(3), resSet.getInt(4),
                    resSet.getDouble(5),resSet.getDate(6),
                    resSet.getTime(7), resSet.getString(8)));
        }
        resSet.close();

        return list;
    }

    public ArrayList<Order> getOrdersOfUser(String option, String dataSearching) throws SQLException, ParseException { //string str

        String searchQuery = "";
        switch(option) {
            case "product_name":
                searchQuery = " where products.product_name = ";
                break;
            case "user_name":
                searchQuery = " where users.user_name = ";
                break;
            case "date":
                searchQuery = " where orders.order_date = ";
                break;
            case "price":
                searchQuery = " where orders.order_price = ";
                break;
        }

        String query = "select orders.id, users.user_name, products.product_name, orders.amount," +
                " orders.order_price, orders.order_date," +
                " orders.order_time, orders.status" +
                " from orders inner join users on orders.userId = users.id" +
                " inner join products on orders.productId = products.id" + searchQuery + "?";
               // " where users.user_name = ?";
        PreparedStatement st = connection.prepareStatement(query);
        switch(option) {
            case "product_name":
                st.setString(1, dataSearching);
                break;
            case "user_name":
                st.setString(1, dataSearching);
                break;
            case "date":
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd.MM.yyyy");
                java.util.Date docDate = format.parse(dataSearching);
                Date date = new Date(docDate.getTime());
                System.out.println(date);
                st.setDate(1, date);
                //st.setString(1, "'" + dataSearching + "'");
                break;
            case "price":
                st.setDouble(1, Double.parseDouble(dataSearching));
                break;
        }
        st.addBatch();
        ResultSet resSet = st.executeQuery();
        ArrayList<Order> list = new ArrayList<>();

        while(resSet.next()) {
            list.add(new Order(resSet.getInt(1), resSet.getString(2),
                    resSet.getString(3), resSet.getInt(4),
                    resSet.getDouble(5),resSet.getDate(6),
                    resSet.getTime(7), resSet.getString(8)));
        }
        resSet.close();
        return list;
    }

    public String addProd(Product pr) throws SQLException {

        PreparedStatement st = connection.prepareStatement("insert into products (product_name, product_price, amount) values(?,?,?) ");
        st.setString(1, pr.getName());
        st.setDouble(2, pr.getPrice());
        st.setInt(3, pr.getAmount());
        st.addBatch();
        st.executeUpdate();


        st.close();
        return "success";

    }

    public String editProd(Product pr) throws SQLException {

        String query = "select * from products";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        String id = Integer.toString(pr.getId());
        while(resSet.next()) {

            if(resSet.getString(1).equals(id)) {
                flag = true;
                break;
            }
        }
        if(flag) {
            resSet.close();
            st = connection.prepareStatement("update products set product_name = ?, product_price = ?, amount = ? where id = ?");
            st.setString(1, pr.getName());
            st.setDouble(2, pr.getPrice());
            st.setInt(3, pr.getAmount());
            st.setInt(4, pr.getId());
            st.addBatch();
            st.executeBatch();


            st.close();
            return "success";
        } else {
            return " ";
        }

    }

    public String delProd(Product pr) throws SQLException {

        String query = "select * from products";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet resSet = st.executeQuery();
        boolean flag = false;
        String id = Integer.toString(pr.getId());
        while(resSet.next()) {

            if(resSet.getString(1).equals(id)) {
                flag = true;
                break;
            }
        }
        if(flag) {
            resSet.close();
            st = connection.prepareStatement("delete from products where id = ?");
            st.setInt(1, pr.getId());

            st.addBatch();
            st.executeBatch();
            st.close();
            return "success";
        } else {
            return " ";
        }

    }

    public String addOrder(Order or) throws SQLException {
        PreparedStatement st = connection.prepareStatement("select id from users where user_name = ?");
        st.setString(1,or.getUser_name());
        st.addBatch();
        ResultSet resSet = st.executeQuery();

        if(resSet.next()) {
            or.setUser_id(resSet.getInt(1));
        }

        st = connection.prepareStatement("select id from products where product_name = ?");
        st.setString(1,or.getProd_name());
        st.addBatch();
        resSet = st.executeQuery();

        if(resSet.next()) {
            or.setProd_id(resSet.getInt(1));
        }
        resSet.close();
        st = connection.prepareStatement("insert into orders (userId, productId, amount, order_price, order_date, order_time) values(?,?,?,?,?,?) ");
        st.setInt(1, or.getUser_id());
        st.setInt(2, or.getProd_id());
        st.setInt(3, or.getAmount());
        st.setDouble(4,or.getPrice());
        st.setDate(5,or.getDate());
        st.setTime(6,or.getTime());
        st.addBatch();
        st.executeBatch();


        st.close();
        return "success";

    }
    public double getPrice(String prod_name) throws SQLException {
        System.out.println(prod_name);
        String query = "select product_price from products where product_name = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, prod_name);
        st.addBatch();

        ResultSet resSet = st.executeQuery();
        double price = 0;
        int i = 0;
        if(resSet.next()) {
            price = resSet.getDouble(1);
            i++;
        }
        if(i == 0) {
            resSet.close();
            st.close();
            return -1;
        }
       // System.out.println(resSet.getDouble(1));
        resSet.close();
        st.close();
        return price;
    }

    public String getStatus(String id) throws SQLException {
        int orderId = Integer.parseInt(id);
        String query = "select status from orders where id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, orderId);
        st.addBatch();

        ResultSet resSet = st.executeQuery();
        String status = "";
        int i = 0;
        if(resSet.next()) {
            status = resSet.getString(1);
            i++;
        }
        if(i == 0) {
            resSet.close();
            st.close();
            return status;
        }
        // System.out.println(resSet.getDouble(1));
        resSet.close();
        st.close();
        return status;
    }

    public String updateOrderStatus(String id, String newStatus) throws SQLException {
        int orderId = Integer.parseInt(id);
        String query = "update orders set status = ? where id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, newStatus);
        st.setInt(2, orderId);
        st.addBatch();

        st.executeBatch();


        return "success";
    }


    public ResultSet fun(String exp) throws SQLException {
        PreparedStatement st = connection.prepareStatement(exp);
        return st.executeQuery();
    }


    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }
}
