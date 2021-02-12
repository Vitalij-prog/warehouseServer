package dao;

import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    public static UserDao dao;
    private static User user;

    @BeforeAll
    public static void initialize() {
        dao = new UserDao();
        user = new User(0,"user", "user", "client","active");
    }

    @org.junit.jupiter.api.Test
    void TestAddMethod() {
        assertEquals("success",dao.add(user));
        assertEquals("",dao.add(user));
    }

    @org.junit.jupiter.api.Test
    void TestUserDaoMethod() {
        assertEquals("success",dao.add(user));
        User bufUser = dao.searchBy("имени", user.getUserName()).get(0);
        assertEquals("user", bufUser.getUserName());
        assertEquals("success", dao.deleteById(bufUser.getId()));
    }

    @org.junit.jupiter.api.Test
    void TestSearchForAuthorizationMethod() {
        User bufUser = dao.searchForAuthorization(user.getUserName(),user.getPassword());
        assertEquals("client", bufUser.getRole());
        assertEquals("user", bufUser.getUserName());
    }

    @Test
    void TestSearchByMethod() {
        User bufUser = dao.searchBy("имени", user.getUserName()).get(0);
        assertEquals("user", bufUser.getUserName());
    }

    @org.junit.jupiter.api.Test
    void TestDeleteById() {
        System.out.println();
        assertEquals("success", dao.deleteById(15));
    }
}