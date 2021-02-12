package entities;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private Integer amount;
    private Integer manufacturer_id;
    private String type;
    private String manufacturer;

    public Product(int id, String name, int amount, double price, int manufacturer_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.manufacturer_id = manufacturer_id;
    }
    public Product(int id, String name, double price, int amount, String type, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.manufacturer = manufacturer;
    }
    public Product(int amount,  String type) {

        this.amount = amount;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getType() { return type; }

    public Integer getManufacturerId() { return manufacturer_id; }

    public String getManufacturer() { return manufacturer; }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}

