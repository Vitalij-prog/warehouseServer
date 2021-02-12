package dao;

import entities.Manufacturer;
import entities.Product;
import jdbc.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDao extends Util implements Dao<Manufacturer> {

    @Override
    public String add(Manufacturer manufacturer) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLManufacturer.SEARCH_BY_NAME.QUERY);
            statement.setString(1, manufacturer.getName());
            resSet = statement.executeQuery();
            if(resSet.next()) {
                return "";
            }
            statement = connection.prepareStatement(SQLManufacturer.ADD_MANUFACTURER.QUERY);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getProductsType());

            result = statement.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e);
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
        return "success";
    }

    @Override
    public Manufacturer getById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        Manufacturer manufacturer = null;
        ResultSet resSet = null;
        try {
            statement = connection.prepareStatement(SQLManufacturer.GET_BY_ID.QUERY);
            statement.setInt(1, id);
            resSet = statement.executeQuery();

            if (resSet.next()) {
                manufacturer  = new Manufacturer(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("ManufacturerDao: " + e);
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
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getList() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Manufacturer> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLManufacturer.GET_LIST_OF_MANUFACTURERS.QUERY);
            resSet = statement.executeQuery();

            while (resSet.next()) {
                list.add(new Manufacturer(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3)));
            }
            resSet.close();
        } catch (SQLException e) {
            System.out.println("ManufacturerDao: " + e);
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

        return list;
    }


    @Override
    public List<Manufacturer> searchBy(String criterion, String data) {
        return null;
    }

    @Override
    public String setById(Manufacturer manufacturer) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLManufacturer.SET_BY_ID.QUERY);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getProductsType());
            statement.setInt(3, manufacturer.getId());
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
    public String deleteById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLManufacturer.DEL_BY_ID.QUERY);
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

enum SQLManufacturer {
    GET_LIST_OF_MANUFACTURERS("select * from manufacturers"),
    ADD_MANUFACTURER("insert into manufacturers(manufacturer_name, products_type) values(?,?)"),
    DEL_BY_ID("delete from manufacturers where manufacturer_id = ?"),
    SEARCH_BY_NAME("select * from manufacturers where manufacturer_name = ?"),
    GET_BY_ID("select * from manufacturers where manufacturer_id = ?"),
    SET_BY_ID("update manufacturers set manufacturer_name = ?, products_type = ? where manufacturer_id = ?");
    String QUERY;
    SQLManufacturer(String QUERY) {
        this.QUERY = QUERY;
    }
}
