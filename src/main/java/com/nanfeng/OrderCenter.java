package com.nanfeng;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public interface OrderCenter {

    //添加订单
    public void addOrder(Order order);

    //移除订单
    public void removeOrder(Order order);

    //所有订单信息
    public String ordersTable();

    //指定订单信息
    public String orderTable(String orderId);

    //存储订单
    public void storeOrders();

    //加载订单
    public void loadOrders();

}
