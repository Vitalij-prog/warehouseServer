package dao;


import entities.Product;
import entities.User;
import jdbc.Util;
import server.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Util implements Dao<User> {
    @Override
    public String add(User user) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLUser.SEARCH_USER_BY_USERNAME.QUERY);
            statement.setString(1, user.getUserName());
            resSet = statement.executeQuery();

            if(resSet.next()) {
                return "";
            } else {
                statement = connection.prepareStatement(SQLUser.ADD_USER.QUERY);
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole());
                statement.setString(4, user.getStatus());
                result = statement.executeUpdate();
            }

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
        if(result == 0) {
            return "";
        }
        return "success";
    }

    @Override
    public User getById(int id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        User user = null;
        ResultSet resSet = null;
        try {
            statement = connection.prepareStatement(SQLUser.GET_BY_ID.QUERY);
            statement.setInt(1, id);
            resSet = statement.executeQuery();

            if (resSet.next()) {
                user = new User(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getString(4),
                        resSet.getString(5));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("UserDao: " + e);
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
        return user;
    }

    @Override
    public List<User> getList() {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SQLUser.GET_LIST_OF_USERS.QUERY);
            resSet = statement.executeQuery();


            while (resSet.next()) {
                users.add(new User(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getString(4),
                        resSet.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println("UserDao: " + e);
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

        return users;
    }

    @Override
    public List<User> searchBy(String criterion, String data) {
        String condition = "";
        ArrayList<User> users = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resSet = null;
        try {
            switch(criterion){
                case "номеру":
                    statement = connection.prepareStatement(SQLUser.GET_LIST_OF_USERS.QUERY +
                            " " + SQLUser.SEARCH_BY_ID.QUERY);
                    statement.setInt(1,Integer.parseInt(data));
                    break;
                case "имени":
                    statement = connection.prepareStatement(SQLUser.GET_LIST_OF_USERS.QUERY +
                            " " + SQLUser.SEARCH_BY_NAME.QUERY);
                    statement.setString(1, data);
                    break;
                case "роли":
                    statement = connection.prepareStatement(SQLUser.GET_LIST_OF_USERS.QUERY +
                            " " + SQLUser.SEARCH_BY_ROLE.QUERY);
                    statement.setString(1,data);
                    break;
                case "статусу":
                    statement = connection.prepareStatement(SQLUser.GET_LIST_OF_USERS.QUERY +
                            " " + SQLUser.SEARCH_BY_STATUS.QUERY);
                    statement.setString(1, data);
                    break;
            }

            resSet = statement.executeQuery();

            while (resSet.next()) {
                users.add(new User(resSet.getInt(1),
                        resSet.getString(2),"",
                        resSet.getString(4),
                        resSet.getString(5)));
            }
            resSet.close();
        } catch (SQLException e) {
            System.out.println("UserDao: " + e);
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

        return users;
    }

    @Override
    public String setById(User user) {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLUser.SET_BY_ID.QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus());
            statement.setInt(4, user.getId());
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
            statement = connection.prepareStatement(SQLUser.DEL_BY_ID.QUERY);
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

    public User searchForAuthorization(String userName, String password) {
        Connection connection = getConnection();
        //String query = "select * from users where user_name = ?";
        PreparedStatement statement = null;
        ResultSet resSet = null;
        String role = "";
        User user = null;
        try {
            statement = connection.prepareStatement(SQLUser.SEARCH_USER_BY_USERNAME_AND_PASSWORD.QUERY);
            statement.setString(1, userName);
            statement.setString(2, password);
            resSet = statement.executeQuery();

            if(resSet.next()) {
                user = new User(resSet.getInt(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        resSet.getString(4),
                        resSet.getString(5));

            }
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
        return user;
    }

    public String register(User user) {
        Connection connection = getConnection();
        //String query = "select * from users where user_name = ?";
        PreparedStatement statement = null;
        PreparedStatement statementAdd = null;
        ResultSet resSet = null;
        String defaultStatus = "active";
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLUser.SEARCH_USER_BY_USERNAME.QUERY);
            statement.setString(1, user.getUserName());
            resSet = statement.executeQuery();

            if(resSet.next()) {
                return "";
            } else {
                statementAdd = connection.prepareStatement(SQLUser.ADD_USER.QUERY);
                statementAdd.setString(1, user.getUserName());
                statementAdd.setString(2, user.getPassword());
                statementAdd.setString(3, user.getRole());
                statementAdd.setString(4, defaultStatus);
                result = statementAdd.executeUpdate();
            }
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
                if(statementAdd != null) {
                    statementAdd.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
        }
        if(result == 0) {
            return "";
        }
        return "success";
    }

    public String updateName(User user) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        PreparedStatement statementAdd = null;
        ResultSet resSet = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQLUser.SEARCH_USER_BY_USERNAME.QUERY);
            statement.setString(1, user.getUserName());
            resSet = statement.executeQuery();

            if(resSet.next()) {
                return "";
            } else {
                statementAdd = connection.prepareStatement(SQLUser.SET_BY_ID.QUERY);
                statementAdd.setString(1, user.getUserName());
                statementAdd.setString(2, user.getPassword());
                statementAdd.setString(3, user.getStatus());
                statementAdd.setInt(4, user.getId());
                result = statementAdd.executeUpdate();
            }
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
                if(statementAdd != null) {
                    statementAdd.close();
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

    public String setStatus(User user) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        int result = 0;
        try {
            statement = connection.prepareStatement(SQLUser.UPDATE_STATUS.QUERY);
            statement.setString(1, user.getStatus());
            statement.setInt(2, user.getId());
            statement.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            try {
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

    /*public ArrayList<User> getUsersList() {
        MyDB mydb = null;
        ArrayList<User> list = null;
        try {
            mydb = new MyDB();
            list = mydb.getUsers();
            mydb.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(User user) {

    }*/
}

enum SQLUser {
    SEARCH_USER_BY_USERNAME_AND_PASSWORD("select * from users where user_name = ? and user_password = ?"),
    SEARCH_USER_BY_USERNAME("select * from users where user_name = ?"),
    GET_LIST_OF_USERS("select * from users"),
    GET_BY_ID("select * from users where user_id = ?"),
    ADD_USER("insert into users (user_name, user_password, user_role, user_status) values(?,?,?,?)"),
    DEL_BY_ID("delete from users where user_id = ?"),
    SET_BY_ID("update users set user_name = ?, user_password = ?, user_status = ? where user_id = ?"),
    SEARCH_BY_ID("where users.user_id = ?"),
    SEARCH_BY_NAME("where users.user_name = ?"),
    SEARCH_BY_ROLE("where users.user_role = ?"),
    SEARCH_BY_STATUS("where users.user_status = ?"),
    UPDATE_STATUS("update users set user_status = ? where user_id = ?");
    String QUERY;

    SQLUser(String QUERY) {
        this.QUERY = QUERY;
    }
}
