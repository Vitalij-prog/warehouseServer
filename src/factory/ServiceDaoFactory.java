package factory;

import services.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServiceDaoFactory {

    Service service = null;

    public Service createDaoService(String type, ObjectOutputStream writer, ObjectInputStream reader) {

        switch(type) {
            case "user":
                service = new ServiceUserDao(writer, reader);
                break;
            case "product":
                service = new ServiceProductDao(writer, reader);
                break;
            case "manufacturer":
                service = new ServiceManufacturerDao(writer, reader);
                break;
            case "order":
                service = new ServiceOrderDao(writer, reader);
                break;
            case "supply":
                service = new ServiceSupplyDao(writer, reader);
                break;
        }
        return  service;
    }
}
