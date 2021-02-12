package services;

import dao.ProductDao;
import entities.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServiceProductDao extends Service {

    public ServiceProductDao(ObjectOutputStream writer, ObjectInputStream reader) {
        super(writer, reader);
    }

    @Override
    public void execute(String request) {
        ProductDao dao = new ProductDao();
        Product product;
        List<Product> products;
        int id;
        String answer;
        try {
            switch (request) {
                case "getList":
                    products = dao.getList();
                    writer.writeObject(products);
                    break;
                case "getByName":
                    answer = (String) reader.readObject();
                    product = dao.getByName(answer);
                    writer.writeObject(product);
                    break;
                case "add":
                    product = (Product) reader.readObject();
                    answer = dao.add(product);
                    writer.writeObject(answer);
                    break;
                case "addFromSupply":
                    product = (Product) reader.readObject();
                    answer = dao.addFromSupply(product);
                    writer.writeObject(answer);
                    break;
                case "get":
                    id = (Integer) reader.readObject();
                    product = dao.getById(id);
                    writer.writeObject(product);
                    break;
                case "getInfo":
                    products = dao.getInfoProducts();
                    writer.writeObject(products);
                    break;
                case "del":
                    id = (Integer) reader.readObject();
                    answer = dao.deleteById(id);
                    writer.writeObject(answer);
                    break;
                case "set":
                    product = (Product) reader.readObject();
                    answer = dao.setById(product);
                    writer.writeObject(answer);
                    break;
                case "search":
                    String condition = (String) reader.readObject();
                    String data = (String) reader.readObject();
                    products = dao.searchBy(condition, data);
                    writer.writeObject(products);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Product Service Exception" + e);
        } finally {
            freeUpResources();
        }
    }
}
