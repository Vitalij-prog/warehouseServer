package dao;

import entities.Product;
import jdbc.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends Util implements Dao<Product> {

    @Override
    public String add(Product product) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLProduct.ADD_PRODUCT.QUERY);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getAmount());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getManufacturerId());
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

    public String addFromSupply(Product product) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        try {
            statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY + " " + SQLProduct.SEARCH_BY_NAME.QUERY);
            statement.setString(1, product.getName());

            resSet = statement.executeQuery();

            if(resSet.next()) {
                statement = connection.prepareStatement(SQLProduct.ADD_AMOUNT_BY_NAME.QUERY);
                statement.setInt(1, product.getAmount());
                statement.setString(2, product.getName());
                statement.executeUpdate();
                System.out.println("add amount to product");
            } else {

                statement = connection.prepareStatement(SQLProduct.ADD_PRODUCT.QUERY);
                statement.setString(1, product.getName());
                statement.setInt(2, product.getAmount());
                statement.setDouble(3, product.getPrice());
                statement.setInt(4, product.getManufacturerId());
                statement.executeUpdate();
                System.out.println("add new product");
            }
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
    public Product getById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        Product product = null;
        ResultSet resSet = null;
        try {
            statement = connection.prepareStatement(SQLProduct.GET_BY_ID.QUERY);
            statement.setInt(1, id);
            resSet = statement.executeQuery();

            if (resSet.next()) {
                product  = new Product(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getInt(3),
                        resSet.getDouble(4),
                        resSet.getInt(5));
            } else {
                return null;
            }
        } catch (SQLException e) {
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
        return product;
    }

    public Product getByName(String name) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        Product product = null;
        ResultSet resSet = null;
        try {
            statement = connection.prepareStatement(SQLProduct.GET_BY_NAME.QUERY);
            statement.setString(1, name);
            resSet = statement.executeQuery();

            if (resSet.next()) {
                product  = new Product(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getInt(3),
                        resSet.getDouble(4),
                        resSet.getInt(5));
            } else {
                return null;
            }
        } catch (SQLException e) {
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
        return product;
    }

    @Override
    public List<Product> getList() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY );
            resSet = statement.executeQuery();


            while (resSet.next()) {
                products.add(new Product(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getDouble(3),
                        resSet.getInt(4),
                        resSet.getString(5),
                        resSet.getString(6)));
            }
            resSet.close();
        } catch (SQLException e) {
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

        return products;
    }

    public List<Product> getInfoProducts() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<Product> products = new ArrayList<>();
        int numberOfProducts = 0;
        try {
            statement = connection.prepareStatement(SQLProduct.COUNT_PRODUCTS.QUERY);
            resSet = statement.executeQuery();


            if(resSet.next()) {
                numberOfProducts = resSet.getInt(1);
            }
            statement = connection.prepareStatement(SQLProduct.GET_PRODUCTS_BY_TYPE.QUERY);
            resSet = statement.executeQuery();

            while (resSet.next()) {
                products.add(new Product((resSet.getInt(1) / numberOfProducts * 100),
                        resSet.getString(2)));

            }



        } catch (SQLException e) {
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

        return products;
    }

    @Override
    public List<Product> searchBy(String criterion, String data) {

        String condition = "";
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        try {
            switch(criterion){
                case "номеру":
                    condition = SQLProduct.SEARCH_BY_ID.QUERY;
                    statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY + " " + condition);
                    statement.setInt(1,Integer.parseInt(data));
                    break;
                case "названию":
                    condition = SQLProduct.SEARCH_BY_NAME.QUERY;
                    statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY + " " + condition);
                    statement.setString(1, data);
                    break;
                case "количеству":
                    condition = SQLProduct.SEARCH_BY_AMOUNT.QUERY;
                    statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY + " " + condition);
                    statement.setInt(1,Integer.parseInt(data));
                    break;
                case "производителю":
                    condition = SQLProduct.SEARCH_BY_MANUFACTURER.QUERY;
                    statement = connection.prepareStatement(SQLProduct.GET_LIST_OF_PRODUCTS.QUERY + " " + condition);
                    statement.setString(1, data);
                    break;
            }

            resSet = statement.executeQuery();

            while (resSet.next()) {
                products.add(new Product(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getDouble(3),
                        resSet.getInt(4),
                        resSet.getString(5),
                        resSet.getString(6)));
            }
            resSet.close();
        } catch (SQLException e) {
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

        return products;
    }

    @Override
    public String setById(Product product) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLProduct.SET_BY_ID.QUERY);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getAmount());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getId());
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
            statement = connection.prepareStatement(SQLProduct.DEL_BY_ID.QUERY);
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

enum SQLProduct {
    GET_LIST_OF_PRODUCTS("select product_id, product_name, product_price, " +
            "product_amount, manufacturers.products_type, manufacturers.manufacturer_name " +
            "from products inner join manufacturers on products.manufacturer_id = manufacturers.manufacturer_id"),
    ADD_PRODUCT("insert into products (product_name, product_amount, product_price, manufacturer_id) values(?,?,?,?)"),
    GET_BY_ID("select * from products where product_id = ?"),
    GET_BY_NAME("select * from products where product_name = ?"),
    DEL_BY_ID("delete from products where product_id = ?"),
    SET_BY_ID("update products set product_name = ?, product_amount = ?, product_price = ? where product_id = ?"),
    ADD_AMOUNT_BY_NAME("update products set product_amount = product_amount + ? where product_name = ?"),

    SEARCH_BY_ID("where products.product_id = ?"),
    SEARCH_BY_NAME("where products.product_name = ?"),
    SEARCH_BY_AMOUNT("where products.product_amount = ?"),
    SEARCH_BY_MANUFACTURER("where manufacturers.manufacturer_name = ?"),
    COUNT_PRODUCTS("select product_id from products"),
    GET_PRODUCTS_BY_TYPE("select count(product_id), manufacturers.products_type " +
            "from products inner join manufacturers on products.manufacturer_id = manufacturers.manufacturer_id " +
            "group by (manufacturers.products_type)");

    String QUERY;

    SQLProduct(String QUERY) {
        this.QUERY = QUERY;
    }
}
