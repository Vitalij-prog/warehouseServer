package dao;

import entities.Order;
import entities.Supply;
import entities.Product;
import jdbc.Util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SupplyDao extends Util implements Dao<Supply> {


    @Override
    public String add(Supply supply) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLSupply.ADD_SUPPLY.QUERY);
            statement.setInt(1, supply.getUserId());
            statement.setString(2, supply.getProductName());
            statement.setDouble(3, supply.getProductPrice());
            statement.setInt(4, supply.getProductAmount());
            statement.setInt(5, supply.getManufacturerId());
            statement.setDate(6, supply.getDate());
            statement.setTime(7, supply.getTime());
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
    public Supply getById(int id) {
        return null;
    }

    @Override
    public List<Supply> getList() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Supply> supplies = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY);
            resSet = statement.executeQuery();


            while (resSet.next()) {
                supplies.add(new Supply(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getDouble(4),
                        resSet.getInt(5),
                        resSet.getString(6),
                        resSet.getDate(7),
                        resSet.getTime(8),
                        resSet.getString(9),0));
            }
        } catch (SQLException e) {
            System.out.println("SupplyDao: " + e);
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

        return supplies;
    }

    public List<Supply> getListByUserId(int userId) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Supply> supplies = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + SQLSupply.BY_USER_ID.QUERY);
            statement.setInt(1,userId);
            resSet = statement.executeQuery();
            while (resSet.next()) {
                supplies.add(new Supply(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getDouble(4),
                        resSet.getInt(5),
                        resSet.getString(6),
                        resSet.getDate(7),
                        resSet.getTime(8),
                        resSet.getString(9),0));
            }
        } catch (SQLException e) {
            System.out.println("SupplyDao: " + e);
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
        return supplies;
    }

    public List<Supply> getListByStatus(String status) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Supply> supplies = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + SQLSupply.BY_STATUS.QUERY);
            statement.setString(1, status);
            resSet = statement.executeQuery();
            while (resSet.next()) {
                supplies.add(new Supply(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getDouble(4),
                        resSet.getInt(5),
                        resSet.getString(6),
                        resSet.getDate(7),
                        resSet.getTime(8),
                        resSet.getString(9),
                        resSet.getInt(10)));
            }
        } catch (SQLException e) {
            System.out.println("SupplyDao: " + e);
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
        return supplies;
    }

    public List<Supply> getSuppliesLastDays() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Supply> supplies = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLSupply.GET_SUPPLIES_LAST_DAYS.QUERY );
            resSet = statement.executeQuery();
            while (resSet.next()) {
                supplies.add(new Supply(resSet.getInt(1),
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

        return supplies;
    }

    public String getProviderInfoById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        String result = "";
        try {
            statement = connection.prepareStatement(SQLSupply.GET_PROVIDER_INFO_BY_ID.QUERY );
            statement.setInt(1, id);
            resSet = statement.executeQuery();
            if(resSet.next()) {
                result = resSet.getInt(1) + "/" +
                        resSet.getInt(2) + "/" +
                        resSet.getDouble(3);
            }
        } catch (SQLException e) {
            System.out.println("SupplyDao: " + e);
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
    public List<Supply> searchBy(String criterion, String data) {
        String condition = "";
        ArrayList<Supply> supplies = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        try {
            switch(criterion){
                case "номеру":
                    condition = SQLSupply.SEARCH_BY_ID.QUERY;
                    statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + " " + condition);
                    statement.setInt(1,Integer.parseInt(data));
                    break;
                case "имени пользователя":
                    condition = SQLSupply.SEARCH_BY_USER_NAME.QUERY;
                    statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + " " + condition);
                    statement.setString(1, data);
                    break;
                case "названию товара":
                    condition = SQLSupply.SEARCH_BY_PRODUCT_NAME.QUERY;
                    statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + " " + condition);
                    statement.setString(1,data);
                    break;
                case "дате поставки":
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("dd.MM.yyyy");
                    java.util.Date docDate = format.parse(data);
                    Date date = new Date(docDate.getTime());
                    condition = SQLSupply.SEARCH_BY_SUPPLY_DATE.QUERY;
                    statement = connection.prepareStatement(SQLSupply.GET_LIST_OF_SUPPLIES.QUERY + " " + condition);
                    statement.setDate(1, date);
                    break;
            }

            resSet = statement.executeQuery();

            while (resSet.next()) {
                supplies.add(new Supply(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getDouble(4),
                        resSet.getInt(5),
                        resSet.getString(6),
                        resSet.getDate(7),
                        resSet.getTime(8),
                        resSet.getString(9),
                        resSet.getInt(10)));
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

        return supplies;
    }

    @Override
    public String setById(Supply supply) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLSupply.SET_STATUS_BY_ID.QUERY);
            statement.setString(1, supply.getStatus());
            statement.setInt(2, supply.getId());
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
            statement = connection.prepareStatement(SQLSupply.DEL_BY_ID.QUERY);
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

enum SQLSupply {
    GET_LIST_OF_SUPPLIES("select supply_id, users.user_name, supplies.product_name, " +
            "supplies.product_price, supplies.product_amount, manufacturers.manufacturer_name, " +
            "supply_date, supply_time, supply_status , manufacturers.manufacturer_id " +
            "from supplies inner join users on supplies.user_id = users.user_id " +
            "inner join manufacturers on supplies.manufacturer_id = manufacturers.manufacturer_id"),
    GET_PROVIDER_INFO_BY_ID("select COUNT(supply_id), SUM(product_amount), SUM(product_amount * product_price) " +
            "from supplies where supply_status = 'confirmed' AND user_id = ?"),
    BY_USER_ID(" where users.user_id = ?"),
    BY_STATUS(" where supply_status = ?"),
    ADD_SUPPLY("insert into supplies (user_id, product_name, product_price, product_amount, manufacturer_id, supply_date, supply_time) values(?,?,?,?,?,?,?)"),
    //GET_BY_ID("select * from products where product_id = ?"),
    DEL_BY_ID("delete from supplies where supply_id = ?"),
    SET_STATUS_BY_ID("update supplies set supply_status = ? where supply_id = ?"),
    GET_SUPPLIES_LAST_DAYS("select count(supply_id), supply_date " +
                                    "from supplies " +
                                    "where supply_date between curdate() - interval 10 DAY and curdate() " +
                                    "group by (supply_date) " +
                                    "order by supply_date"),
    SEARCH_BY_ID("where supply_id = ?"),
    SEARCH_BY_USER_NAME("where users.user_name = ?"),
    SEARCH_BY_PRODUCT_NAME("where supplies.product_name = ?"),
    SEARCH_BY_SUPPLY_DATE("where supplies.supply_date = ?");
    String QUERY;

    SQLSupply(String QUERY) {
        this.QUERY = QUERY;
    }
}
