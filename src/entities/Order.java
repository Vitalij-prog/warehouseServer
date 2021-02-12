package entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Order implements Serializable {
    private int id;
    private String user_name;
    private String prod_name;
    private int amount;
    private double price;
    private Date date;
    private Time time;
    private String status;
    private int user_id;
    private int prod_id;

    public Order(int id, String user_name, String prod_name, int amount, double price, Date date, Time time, String status){
        this.id = id;
        this.user_name = user_name;
        this.prod_name = prod_name;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.time = time;
        this.status = status;
    }
    public Order(int user_id, int prod_id, int amount, double price, Date date, Time time){
        this.user_id = user_id;
        this.prod_id = prod_id;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public Order(int amount, String date) {
        this.amount = amount;
        this.prod_name = date;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

    public int getId() {
        return this.id;
    }
    public int getAmount() {
        return this.amount;
    }
    public String getUser_name() {
        return this.user_name;
    }
    public String getProd_name() {
        return this.prod_name;
    }
    public double getPrice() {
        return this.price;
    }
    public Date getDate() {
        return this.date;
    }
    public Time getTime() {
        return this.time;
    }
    public String getStatus() {
        return this.status;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public int getUser_id() {
        return user_id;
    }
    public int getProd_id() {
        return prod_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
