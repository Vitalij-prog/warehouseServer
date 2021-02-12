package services;

import dao.ManufacturerDao;
import entities.Manufacturer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServiceManufacturerDao extends Service {
    public ServiceManufacturerDao(ObjectOutputStream writer, ObjectInputStream reader) {
        super(writer, reader);
    }

    @Override
    public void execute(String request) {
        ManufacturerDao dao = new ManufacturerDao();
        Manufacturer manufacturer;
        String answer;
        int id;
        try {
            switch (request) {
                case "getList":
                    List<Manufacturer> list;
                    list = dao.getList();
                    writer.writeObject(list);
                    break;
                case "add":
                    manufacturer = (Manufacturer) reader.readObject();
                    answer = dao.add(manufacturer);
                    writer.writeObject(answer);
                    break;
                case "get":
                    id = (Integer) reader.readObject();
                    manufacturer = dao.getById(id);
                    writer.writeObject(manufacturer);
                    break;
                case "set":
                    manufacturer = (Manufacturer) reader.readObject();
                    answer = dao.setById(manufacturer);
                    writer.writeObject(answer);
                    break;
                case "del":
                    id = (Integer) reader.readObject();
                    answer = dao.deleteById(id);
                    writer.writeObject(answer);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Manufacturer Service Exception" + e);
        } finally {
            freeUpResources();
        }
    }
}
