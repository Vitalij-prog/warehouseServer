package entities;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int id;
    private String name;
    private String productsType;

    public Manufacturer(String name, String productsType) {
        this.name = name;
        this.productsType = productsType;
    }
    public Manufacturer(int id, String name, String productsType) {
        this.id = id;
        this.name = name;
        this.productsType = productsType;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getProductsType() {
        return productsType;
    }

    @Override
    public String toString() {
        return  name;
    }
}
