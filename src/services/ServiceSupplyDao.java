package services;

import dao.SupplyDao;
import entities.Supply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServiceSupplyDao extends Service {
    public ServiceSupplyDao(ObjectOutputStream writer, ObjectInputStream reader) {
        super(writer, reader);
    }

    @Override
    public void execute(String request) {
        SupplyDao dao = new SupplyDao();
        Supply supply;
        List<Supply> supplies;
        int id;
        String answer;
        try {
            switch (request) {
                case "getList":
                    supplies = dao.getList();
                    writer.writeObject(supplies);
                    break;
                case "getListByUserId":
                    id = (Integer) reader.readObject();
                    supplies = dao.getListByUserId(id);
                    writer.writeObject(supplies);
                    break;
                case "getListByStatus":
                    answer = (String) reader.readObject();
                    supplies = dao.getListByStatus(answer);
                    writer.writeObject(supplies);
                    break;
                case "getSuppliesLastDays":
                    supplies = dao.getSuppliesLastDays();
                    writer.writeObject(supplies);
                    break;
                case "getProviderInfoById":
                    id = (Integer) reader.readObject();
                    answer = dao.getProviderInfoById(id);
                    writer.writeObject(answer);
                    break;
                case "add":
                    supply = (Supply) reader.readObject();
                    answer = dao.add(supply);
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
                    supply = (Supply) reader.readObject();
                    answer = dao.setById(supply);
                    writer.writeObject(answer);
                    break;
                case "search":
                    String condition = (String) reader.readObject();
                    String data = (String) reader.readObject();
                    supplies = dao.searchBy(condition, data);
                    writer.writeObject(supplies);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Supply Service Exception" + e);
        } finally {
            freeUpResources();
        }
    }
}
