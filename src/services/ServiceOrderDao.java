package services;

import dao.OrderDao;
import entities.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServiceOrderDao extends Service {
    public ServiceOrderDao(ObjectOutputStream writer, ObjectInputStream reader) {
        super(writer, reader);
    }

    @Override
    public void execute(String request) {
        OrderDao dao = new OrderDao();
        Order order;
        List<Order> orders;
        int id;
        String answer;
        try {
            switch (request) {
                case "getList":
                    orders = dao.getList();
                    writer.writeObject(orders);
                    break;
                case "getListByUserId":
                    id = (Integer) reader.readObject();
                    orders = dao.getListByUserId(id);
                    writer.writeObject(orders);
                    break;
                case "getListByUserIdAndStatus":
                    id = (Integer) reader.readObject();
                    answer = (String) reader.readObject();
                    orders = dao.getListByUserId(id, answer);
                    writer.writeObject(orders);
                    break;
                case "getListByStatus":
                    answer = (String) reader.readObject();
                    orders = dao.getListByStatus(answer);
                    writer.writeObject(orders);
                    break;
                case "getClientInfoById":
                    id = (Integer) reader.readObject();
                    answer = dao.getClientInfoById(id);
                    writer.writeObject(answer);
                    break;
                case "getOrdersLast10Days":
                    orders = dao.getOrdersLast10Days();
                    writer.writeObject(orders);
                    break;
                case "add":
                    order = (Order) reader.readObject();
                    answer = dao.add(order);
                    writer.writeObject(answer);
                    break;
                case "get":
                    break;
                case "del":
                    id = (Integer) reader.readObject();
                    answer = dao.deleteById(id);
                    writer.writeObject(answer);
                    break;
                case "set":
                    order = (Order) reader.readObject();
                    answer = dao.setById(order);
                    writer.writeObject(answer);
                    break;
                case "search":
                    String condition = (String) reader.readObject();
                    String data = (String) reader.readObject();
                    orders = dao.searchBy(condition, data);
                    writer.writeObject(orders);
                    break;
            }
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Order Service Exception" + e);
        } finally {
            freeUpResources();
        }
    }
}
