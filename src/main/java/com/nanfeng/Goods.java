package com.nanfeng;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public class Goods {

    //商品编号
    public String id;
    //商品名称
    public String name;
    //商品价格
    public double price;

    public Goods(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("[%2s] %s %.2f",this.getId(),this.getName(),this.getPrice());
    }
}
