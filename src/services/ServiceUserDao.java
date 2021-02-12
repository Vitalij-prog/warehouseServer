package services;

import dao.UserDao;
import entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServiceUserDao extends Service {
    public ServiceUserDao(ObjectOutputStream writer, ObjectInputStream reader) {
        super(writer, reader);
    }

    @Override
    public void execute(String request) {
        UserDao dao = new UserDao();
        List<User> users;
        User user;
        int id;
        String answer;
        try {
            switch (request) {
                case "login":
                    user = (User) reader.readObject();
                    user = dao.searchForAuthorization(user.getUserName(), user.getPassword());
                    writer.writeObject(user);
                    break;
                case "add":
                case "signup":
                    user = (User) reader.readObject();
                    answer = dao.register(user);
                    writer.writeObject(answer);
                    break;
                case "getList":
                    users = dao.getList();
                    writer.writeObject(users);
                    break;
                case "del":
                    id = (Integer) reader.readObject();
                    answer = dao.deleteById(id);
                    writer.writeObject(answer);
                    break;
                case "updateName":
                    user = (User) reader.readObject();
                    answer = dao.updateName(user);
                    writer.writeObject(answer);
                    break;
                case "set":
                    user = (User) reader.readObject();
                    answer = dao.setById(user);
                    writer.writeObject(answer);
                    break;
                case "search":
                    String condition = (String) reader.readObject();
                    String data = (String) reader.readObject();
                    users = dao.searchBy(condition, data);
                    writer.writeObject(users);
                    break;
                case "get":
                    id = (Integer) reader.readObject();
                    user = dao.getById(id);
                    writer.writeObject(user);
                    break;
                case "setStatus":
                    user = (User) reader.readObject();
                    answer = dao.setStatus(user);
                    writer.writeObject(answer);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("User Service Exception" + e);
        } finally {
            freeUpResources();
        }
    }
}
