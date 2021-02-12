package dao;

import entities.Order;
import entities.Product;
import jdbc.Util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends Util implements Dao<Order> {

    @Override
    public String add(Order order) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLOrder.ADD_ORDER.QUERY);
            statement.setInt(1, order.getUser_id());
            statement.setInt(2, order.getProd_id());
            statement.setInt(3, order.getAmount());
            statement.setDouble(4, order.getPrice());
            statement.setDate(5, order.getDate());
            statement.setTime(6, order.getTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        }
        return "success";
    }

    @Override
    public Order getById(int id) {
        return null;
    }

    public String getClientInfoById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        String result = "";
        try {
            statement = connection.prepareStatement(SQLOrder.GET_CLIENT_INFO_BY_ID.QUERY );
            statement.setInt(1, id);
            resSet = statement.executeQuery();
            if(resSet.next()) {
                result = resSet.getInt(1) + "/" +
                        resSet.getInt(2) + "/" +
                        resSet.getDouble(3);
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }

        return result;
    }

    @Override
    public List<Order> getList() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY );
            resSet = statement.executeQuery();


            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getInt(4),
                        resSet.getDouble(5),
                        resSet.getDate(6),
                        resSet.getTime(7),
                        resSet.getString(8)));
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }

        return orders;
    }

    public List<Order> getOrdersLast10Days() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLOrder.GET_ORDERS_LAST_10_DAYS.QUERY );
            resSet = statement.executeQuery();
            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }

        return orders;
    }

    public List<Order> getListByUserId(int userId) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + SQLOrder.BY_USER_ID.QUERY);
            statement.setInt(1,userId);
            resSet = statement.executeQuery();
            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getInt(4),
                        resSet.getDouble(5),
                        resSet.getDate(6),
                        resSet.getTime(7),
                        resSet.getString(8)));
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }
        return orders;
    }

    public List<Order> getListByUserId(int userId, String status) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + SQLOrder.BY_USER_ID_AND_STATUS.QUERY);
            statement.setInt(1,userId);
            statement.setString(2,status);
            resSet = statement.executeQuery();
            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getInt(4),
                        resSet.getDouble(5),
                        resSet.getDate(6),
                        resSet.getTime(7),
                        resSet.getString(8)));
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }
        return orders;
    }

    public List<Order> getListByStatus(String status) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + SQLOrder.BY_STATUS.QUERY);
            statement.setString(1, status);
            resSet = statement.executeQuery();
            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getInt(4),
                        resSet.getDouble(5),
                        resSet.getDate(6),
                        resSet.getTime(7),
                        resSet.getString(8)));
            }
        } catch (SQLException e) {
            System.out.println("OrderDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }
        return orders;
    }

    @Override
    public List<Order> searchBy(String criterion, String data) {
        String condition = "";
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        try {
            switch(criterion){
                case "номеру":
                    condition = SQLOrder.SEARCH_BY_ID.QUERY;
                    statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + " " + condition);
                    statement.setInt(1,Integer.parseInt(data));
                    break;
                case "имени пользователя":
                    condition = SQLOrder.SEARCH_BY_USER_NAME.QUERY;
                    statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + " " + condition);
                    statement.setString(1, data);
                    break;
                case "названию товара":
                    condition = SQLOrder.SEARCH_BY_PRODUCT_NAME.QUERY;
                    statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + " " + condition);
                    statement.setString(1,data);
                    break;
                case "дате заказа":
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("dd.MM.yyyy");
                    java.util.Date docDate = format.parse(data);
                    Date date = new Date(docDate.getTime());
                    condition = SQLOrder.SEARCH_BY_ORDER_DATE.QUERY;
                    statement = connection.prepareStatement(SQLOrder.GET_LIST_OF_ORDERS.QUERY + " " + condition);
                    statement.setDate(1, date);
                    break;
            }

            resSet = statement.executeQuery();

            while (resSet.next()) {
                orders.add(new Order(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getInt(4),
                        resSet.getDouble(5),
                        resSet.getDate(6),
                        resSet.getTime(7),
                        resSet.getString(8)));
            }
        } catch (SQLException | ParseException e) {
            System.out.println("ProductDao: " + e);
        } finally {
            try {
                if (resSet != null) {
                    resSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }

        return orders;
    }

    @Override
    public String setById(Order order) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLOrder.SET_STATUS_BY_ID.QUERY);
            statement.setString(1, order.getStatus());
            statement.setInt(2, order.getId());
            statement.executeUpdate(); // номер строки которую удалили
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return "success";
    }

    @Override
    public String deleteById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLOrder.DEL_BY_ID.QUERY);
            statement.setInt(1, id);
            result = statement.executeUpdate(); // номер строки которую удалили
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if(result == 0) {
            return "";
        }
        return "success";
    }
}

enum SQLOrder {
    GET_LIST_OF_ORDERS("select order_id, users.user_name, products.product_name, " +
            "orders.amount, order_price, order_date, order_time, order_status " +
            "from orders inner join users on orders.user_id = users.user_id " +
            "inner join products on orders.product_id = products.product_id"),
    BY_USER_ID(" where users.user_id = ?"),
    BY_USER_ID_AND_STATUS(" where users.user_id = ? and order_status = ?"),
    BY_STATUS(" where order_status = ?"),
    ADD_ORDER("insert into orders (user_id, product_id, amount, order_price, order_date, order_time) values(?,?,?,?,?,?)"),
    GET_BY_ID("select * from products where product_id = ?"),
    DEL_BY_ID("delete from orders where order_id = ?"),
    SET_STATUS_BY_ID("update orders set order_status = ? where order_id = ?"),
    GET_CLIENT_INFO_BY_ID("select COUNT(order_id), SUM(amount), SUM(order_price) " +
            "from orders where order_status = 'confirmed' AND user_id = ?"),
    GET_ORDERS_LAST_10_DAYS("select count(order_id), order_date " +
            "from orders " +
            "where order_date between curdate() - interval 10 DAY and curdate() " +
            "group by (order_date) " +
            "order by order_date"),
    SEARCH_BY_ID("where orders.order_id = ?"),
    SEARCH_BY_USER_NAME("where users.user_name = ?"),
    SEARCH_BY_PRODUCT_NAME("where products.product_name = ?"),
    SEARCH_BY_ORDER_DATE("where orders.order_date = ?");
    String QUERY;

    SQLOrder(String QUERY) {
        this.QUERY = QUERY;
    }
}
