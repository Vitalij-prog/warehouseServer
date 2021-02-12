package entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Supply implements Serializable {
    private int id;
    private int userId;
    private int productId; // ???????????????
    private int manufacturerId;
    private Date date;
    private Time time;
    private String providerName;
    private String productName;
    private String manufacturerName;
    private double productPrice;
    private int productAmount;
    private String status;

    public Supply (int userId,
                   String productName,
                   double productPrice,
                   int productAmount,
                   int manufacturerId,
                   Date date,
                   Time time) {
        this.userId = userId;
        this.manufacturerId = manufacturerId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
        this.date = date;
        this.time = time;
    }
    public Supply (int id,
                   String providerName,
                   String productName,
                   double productPrice,
                   int productAmount,
                   String manufacturerName,
                   Date date,
                   Time time,
                   String status,
                   int manufacturerId) {
        this.id = id;
        this.providerName = providerName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
        this.manufacturerName = manufacturerName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.manufacturerId = manufacturerId;
    }

    public Supply(int amount, String date) {
        this.productAmount = amount;
        this.productName = date;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public int getManufacturerId() {
        return manufacturerId;
    }
    public String getManufacturerName() {
        return manufacturerName;
    }
    public String getProviderName() {
        return providerName;
    }
    public String getProductName() {
        return productName;
    }
    public String getStatus() {
        return status;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public int getProductAmount() {
        return productAmount;
    }
    public Date getDate() {
        return date;
    }
    public Time getTime() {
        return time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}



