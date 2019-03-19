package com.nanfeng.impl;

import com.nanfeng.Goods;
import com.nanfeng.GoodsCenter;
import com.nanfeng.Order;
import com.nanfeng.OrderCenter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public class SimpleOrderCenter implements OrderCenter {

    private String filePath = System.getProperty("user.dir") + File.separator + "order.text";

    private Map<String,Order> orderMap = new HashMap<>();

    private GoodsCenter goodsCenter;
    //构造方法传值
    public SimpleOrderCenter(GoodsCenter goodsCenter){
        this.goodsCenter = goodsCenter;
    }

    @Override
    public void addOrder(Order order) {
        this.orderMap.put(order.getOrderId(),order);
    }

    @Override
    public void removeOrder(Order order) {
        this.orderMap.remove(order.getOrderId(),order);
    }

    @Override
    public String ordersTable() {
        //订单中心依赖商品中心，通过构造方法传值实现
        StringBuilder sb = new StringBuilder();
        sb.append("==============================");
        sb.append("\n");
        sb.append("订单编号 总价");
        sb.append("\n");
        for(Order order:this.orderMap.values()){
            //  商品编号   数量               取出订单里面的所有商品，放入一个Map
            Map<String,Integer> goodsMap = order.getOrderInfo();
            double totalPrice = 0.0;
            for(Map.Entry<String,Integer> entry:goodsMap.entrySet()){
                String goodsId = entry.getKey();
                Integer goodsCount = entry.getValue();
                Goods goods = goodsCenter.getGoods(goodsId);
                totalPrice += goods.getPrice() * goodsCount;
            }
            sb.append(String.format("%2s\t\t%.2f",order.getOrderId(),totalPrice));
            sb.append("\n");
        }
        sb.append("===============================");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String orderTable(String orderId) {
        StringBuilder sb = new StringBuilder();
        Order order = this.orderMap.get(orderId);
        sb.append("==============================");
        sb.append("\n");
        sb.append("编号："+order.getOrderId());
        sb.append("\n");
        sb.append("打印时间："+LocalDate.now().toString());
        sb.append("\n");
        sb.append("=================================");
        sb.append("\n");
        //      编号：自动生成
        //              名称：订单里有编号，通过商品中心获取到商品，就有商品的名称和单价
        sb.append("编号   名称  数量  单价");
        sb.append("\n");
        double total = 0.0D;
        for(Map.Entry<String,Integer> entry:order.getOrderInfo().entrySet()){
            Goods goods = this.goodsCenter.getGoods(entry.getKey());
            sb.append(String.format("%2s\t\t%s\t%d\t%.2f",
                    entry.getKey(),     //订单中心里key是商品编号，value是数量
                    goods.getName(),
                    entry.getValue(),
                    goods.getPrice()));
            total += goods.getPrice() * entry.getValue();
            sb.append("\n");
        }
        sb.append("=================================");
        sb.append("\n");
        sb.append(String.format("总价：%.2f",total));
        sb.append("\n");
        sb.append("=================================");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void storeOrders() {
//        System.out.println("保存所有订单到文件,每个订单记录：编号和总价");
        File file = new File(filePath);
//        double total = 0.0D;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(Order order:this.orderMap.values()){
               String orderId=order.getOrderId();
               writer.write("order_Id:"+orderId+"\n");
                Map<String,Integer> goodsMap = order.getOrderInfo();
                double totalPrice = 0.0D;
                for(Map.Entry<String,Integer> entry:goodsMap.entrySet()){
                    String goodsId = entry.getKey();
                    Integer goodsCount = entry.getValue();
                    Goods goods = goodsCenter.getGoods(goodsId);
                    totalPrice += goods.getPrice() * goodsCount;
                    writer.write(String.format("%s:%d\n",goodsId,goodsCount));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadOrders() {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        if(file.exists() && file.isFile()){
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                while((line = reader.readLine())!=null){
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //lines
        Order current = null;
        for(String line : lines){
                    if(line.startsWith("order_Id:")){
                        current = new Order(line.split(":")[1]);
                        this.addOrder(current);
                    }else {
                        if(current !=null){
                            String[] segment = line.split(":");
                            current.add(segment[0],Integer.parseInt(segment[1]));
                        }
        }
        }
    }
}
