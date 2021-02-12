package server;


import factory.ServiceDaoFactory;
import services.Service;

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {
    private Socket socket;
    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;
    private int id;

    public ServerThread(Socket socket) throws IOException {
        this.id = socket.getPort();
        this.socket = socket;
        writer = new ObjectOutputStream(this.socket.getOutputStream());
        reader = new ObjectInputStream(this.socket.getInputStream());

    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
        if (writer != null) writer.close();
        if (reader != null) reader.close();
    }

    @Override
    public void run() {
        System.out.println("Server Thread " + id + " running.");
        String query = "";
        String[] data;
        ServiceDaoFactory serviceDaoFactory = new ServiceDaoFactory();
        try {
            while (true) {
                try {
                    query = (String) reader.readObject();
                } catch (EOFException e) {
                    System.out.println("Accept message exception: " + e);
                }
                if (query.equals("exit")) {
                    break;
                }
                data = query.split("/");

                Service serverService = serviceDaoFactory.createDaoService(data[0], writer, reader);
                serverService.execute(data[1]);
            }
            message();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void message() {
        System.out.println("### client #" + id + "# disconnected");
    }
}















                /* switch(data[0]) {
                    case "user":
                        userService(data[1]);
                        break;
                    case "product":
                        productService(data[1]);
                        break;
                    case "manufacturer":
                        manufacturerService(data[1]);
                        break;
                    case "order":
                        orderService(data[1]);
                        break;
                    case "supply":
                        supplyService(data[1]);
                        break;
                }*/
    /*private void userService(String query) throws IOException, ClassNotFoundException {
        UserDao dao = new UserDao();
        List<User> users;
        User user;
        int id;
        String answer;
        switch(query) {
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
    }*/

    /*private void productService(String query) throws IOException, ClassNotFoundException {
        ProductDao dao = new ProductDao();
        Product product;
        List<Product> products;
        int id;
        String answer;
        switch(query) {
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
    }*/
   /* private void manufacturerService(String query) throws IOException, ClassNotFoundException {
        ManufacturerDao dao = new ManufacturerDao();
        Manufacturer manufacturer;
        String answer;
        int id;
        switch(query) {
            case "getList":
                List<Manufacturer> list;
                list = dao.getList();
                writer.writeObject(list);
                break;
            case "add":
                manufacturer = (Manufacturer) reader.readObject();
                answer = dao.add( manufacturer);
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
    }*/

    /*private void orderService(String query) throws IOException, ClassNotFoundException {
        OrderDao dao = new OrderDao();
        Order order;
        List<Order> orders;
        int id;
        String answer;
        switch(query) {
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
    }*/


    /*private void supplyService(String query) throws IOException, ClassNotFoundException {
        SupplyDao dao = new SupplyDao();
        Supply supply;
        List<Supply> supplies;
        int id;
        String answer;
        switch(query) {
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
               *//* id = (Integer) reader.readObject();
                product = dao.getById(id);
                writer.writeObject(product);*//*
            *//*case "getByName":
                answer = (String) reader.readObject();
                order = dao.getByName(answer);
                writer.writeObject(order);
                break;*//*
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
    }*/

 /*   private void authorization(String login, String password) throws IOException{

        String role = "";
        try {
            MyDB mydb = new MyDB();
            //log = mydb.searchUser(login, password);
            role = mydb.searchUser(login, password);
            mydb.close();

        }catch(ClassNotFoundException | SQLException e) {
            System.out.println("Authorization " + e);
        }
        if(!role.equals("")) {
            writer.writeObject(role);
            writer.flush();
        } else {
            writer.writeObject("");
            writer.flush();
        }
    }

    private void registration(String login, String password) throws IOException{

        String answer = "";
        try {
            MyDB mydb = new MyDB();
            //log = mydb.searchUser(login, password);
            answer = mydb.addUser(login, password, "user");
            mydb.close();

        }catch(ClassNotFoundException | SQLException e) {
            System.out.println("Authorization " + e);
        }
        writer.writeObject(answer);
        writer.flush();
    }

    private void getListProducts() throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        ArrayList<Product> list = mydb.getProducts("select * from products");
        mydb.close();

        writer.writeObject(list);
        //writer.flush();
    }
    private void getListProducts(String option, String data) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        ArrayList<Product> list = mydb.getProducts(option, data);
        mydb.close();

        writer.writeObject(list);
        //writer.flush();
    }

    private void getListOrders() throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        ArrayList<Order> list = mydb.getOrders();
        mydb.close();
        writer.writeObject(list);

    }

    private void getListOrders(String option, String dataSearching) throws IOException, ClassNotFoundException, SQLException, ParseException {

        MyDB mydb = new MyDB();
        ArrayList<Order> list = mydb.getOrdersOfUser(option,dataSearching);
        mydb.close();
        writer.writeObject(list);

    }

    private void addData(String entity) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        String answer="";

        switch(entity) {
            case "product":
                 Product pr = (Product) reader.readObject();
                 answer = mydb.addProd(pr);
                break;
            case "order":
                Order or = (Order) reader.readObject();
                answer = mydb.addOrder(or);
                break;
            case "user":
                User us = (User) reader.readObject();
                answer = mydb.addUser(us);
                break;
            case "supply":
                break;
            case "manufacturer":
                break;
        }
        mydb.close();
        writer.writeObject(answer);
    }
    private void editData(String entity) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        String answer="";

        switch(entity) {
            case "product":
                Product pr = (Product) reader.readObject();
                answer = mydb.editProd(pr);
                break;
        }
        mydb.close();
        writer.writeObject(answer);
    }

    private void delData(String entity) throws IOException, ClassNotFoundException, SQLException {
        MyDB mydb = new MyDB();
        String answer="";

        switch(entity) {
            case "product":
                Product pr = (Product) reader.readObject();
                answer = mydb.delProd(pr);
                break;
        }

        mydb.close();
        writer.writeObject(answer);
    }

    private void getPrice(String prod_name) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();

        double price = mydb.getPrice(prod_name);

        mydb.close();

        writer.writeObject(price);
    }

    private void getOrderStatus(String id) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();

        String status = mydb.getStatus(id);

        mydb.close();

        writer.writeObject(status);
    }
    private void updateOrderStatus(String id, String newStatus) throws IOException, ClassNotFoundException, SQLException {
        MyDB mydb = new MyDB();

        String answer = mydb.updateOrderStatus(id, newStatus);

        mydb.close();

        writer.writeObject(answer);
    }

    private void getListUsers() throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        ArrayList<User> list = mydb.getUsers();
        mydb.close();
        writer.writeObject(list);

    }

    private void deleteUser(String id) throws IOException, ClassNotFoundException, SQLException {

        MyDB mydb = new MyDB();
        String answer = mydb.deleteUser(id);
        mydb.close();
        writer.writeObject(answer);

    }*/




