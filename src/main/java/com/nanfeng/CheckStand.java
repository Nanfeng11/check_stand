package com.nanfeng;

import com.nanfeng.impl.SimpleGoodsCenter;
import com.nanfeng.impl.SimpleOrderCenter;

import java.util.Scanner;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public class CheckStand {

    private static Scanner scanner = new Scanner(System.in);

    //商品管理中心
    private static GoodsCenter goodsCenter = new SimpleGoodsCenter();

    //订单管理中心
    private static OrderCenter orderCenter = new SimpleOrderCenter(goodsCenter);

    private static Integer orderId = 0;

    //帮助信息
    public static void helpInfo(){
        System.out.println("*****************欢迎使用收银台系统***********************");
        System.out.println("    [U] 使用   [S] 设置   [P] 保存   [A] 关于   [Q] 退出  ");
        System.out.println("                输入：U    S   P   A   Q进行操作          ");
        System.out.println("*********************************************************");
    }

    //退出编辑
    public static void quit(){
        System.out.println("*********************************************************");
        System.out.println("                  欢迎使用本收银系统，再见！              ");
        System.out.println("*********************************************************");
        System.exit(0);         //虚拟机退出
    }

    //关于信息
    public static void about(){
        System.out.println("*********************************************************");
        System.out.println("                      名称：简易收银台                    ");
        System.out.println("             功能：基于字符界面的收银台操纵系统            ");
        System.out.println("                      作者：Nanfeng                       ");
        System.out.println("*********************************************************");
    }

    //使用信息
    public static void usageInfo(){
        System.out.println("***********************买单功能***************************");

        System.out.println("        [A] 添加商品   [D] 取消商品   [L] 浏览商品         ");
        System.out.println("                  [S] 查看订单   [R] 返回上级             ");
        System.out.println("       输入：A     D     L    S   R   进行操作            ");
        System.out.println("*********************************************************");
    }

    //设置信息
    public static void settingInfo(){
        System.out.println("***********************设置功能***************************");

        System.out.println("        [A] 商品上架   [D] 商品下架   [U] 商品修改         ");
        System.out.println("        [S] 查看商品   [L] 浏览订单   [R] 返回上级         ");
        System.out.println("       输入：A     D     U    S   L    R进行操作           ");
        System.out.println("*********************************************************");
    }

    //使用逻辑
    public static void usage(){
        //创建订单，并且添加到订单管理中心
        Order order = new Order(String.valueOf(++orderId));
        orderCenter.addOrder(order);
        usageInfo();
       // System.out.println(orderCenter.orderTable(order.getOrderId()));
        while (true){
            String line = scanner.nextLine();
            switch (line.trim().toUpperCase()){
                case "S":{
                    System.out.println(orderCenter.orderTable(order.getOrderId()));
                    break;
                }
                case "A":{
                    System.out.println("请输入要购买的商品[编号  数量]（如下格式：1 2）：");
                    String value = scanner.nextLine();
                    String[] infoArray = value.split(" ");
                    if(infoArray.length == 2){
                        Goods goods = goodsCenter.getGoods(infoArray[0]);
                        if(goods!=null){
                            order.add(infoArray[0],Integer.parseInt(infoArray[1]));
                            System.out.println(orderCenter.orderTable(order.getOrderId()));
                            break;
                        }else{
                            System.out.println("没有这个商品！");
                            break;
                        }
                    }
                    System.out.println("请按照格式要求输入信息！");
                    break;
                }
                case "D":{
                    System.out.println("请输入要取消的商品[编号 数量]（如下格式：1 2）：");
                    String value = scanner.nextLine();
                    String[] infoArray = value.split(" ");
                    if(infoArray.length==2){
                        Goods goods = goodsCenter.getGoods(infoArray[0]);
                        if(goods!=null){
                            order.cancel(infoArray[0],Integer.parseInt(infoArray[1]));
                            System.out.println(orderCenter.orderTable(order.getOrderId()));
                            break;
                        }
                    }
                    System.out.println("请按照格式要求输入信息！");
                    break;
                }
                case "L":{
                    System.out.println(goodsCenter.listGoods());
                    break;
                }
                case "R":{
                    return;
                }
                default:{
                    usageInfo();
                }
            }
        }
    }

    //设置逻辑
    public static void setting(){
        settingInfo();
        while (true){
            String line = scanner.nextLine();
            switch (line.toUpperCase()){
                case "A":{
                    System.out.println("请输入上架商品信息（如下格式：1 餐巾纸 1.4）：");
                    Goods goods = readGoods();
                    if(goods == null){
                        System.out.println("请按照格式要求输入信息：");
                        break;
                    }
                    if(goodsCenter.isExitGoods(goods.getId())){
                        System.out.println("上架商品已存在，注意编号不能重复！");
                    }else{
                        goodsCenter.addGoods(goods);
                    }
                    System.out.println(goodsCenter.listGoods());
                    break;
                }
                case "D":{
                    System.out.println("请输入下架商品信息（如下格式：1 ）：");
                    Goods goods = readGoods();
                    if(goods == null){
                        System.out.println("请按照格式要求输入信息：");
                        break;
                    }
                    if(goodsCenter.isExitGoods(goods.getId())){
                        goodsCenter.removeGoods(goods.getId());
                    }else{
                        System.out.println("请输入存在的商品");
                    }
                    System.out.println(goodsCenter.listGoods());
                    break;
                }
                case "U":{
                    System.out.println("请输入修改商品信息（如下格式：1 餐巾纸 1.4）：");
                    Goods goods = readGoods();
                    if(goods == null){
                        System.out.println("请按照格式要求输入信息：");
                        break;
                    }
                    if(goodsCenter.isExitGoods(goods.getId())){
                        goodsCenter.updateGoods(goods);
                    }else{
                        System.out.println("请输入存在的商品");
                    }
                    System.out.println(goodsCenter.listGoods());
                    break;
                }
                case "S":{
                    System.out.println(goodsCenter.listGoods());
                    break;
                }
                case "L":{
                    System.out.println(orderCenter.ordersTable());
                }
                case "R":{
                    return;
                }
                default:{
                    settingInfo();
                }
            }
        }
    }

    //读取商品
    public static Goods readGoods(){
        String value = scanner.nextLine();
        String[] infoArray = value.split(" ");
        if(infoArray==null){
            System.out.println("请按照格式要求输入U");
        }
        if(infoArray.length == 3 || infoArray.length == 1){
            if(infoArray.length == 3){
                Goods goods = new Goods(infoArray[0],
                        infoArray[1],
                        Double.parseDouble(infoArray[2]));
                return goods;
            }
            Goods goods = new Goods(infoArray[0],"",0.0D);
            return goods;
        }
        return null;
    }

    //主方法
    public static void main(String[] args) {
        helpInfo();
        //初始化加载文件信息
        goodsCenter.load();
        orderCenter.loadOrders();
        while(true){
            String line = scanner.nextLine();
            switch(line.trim().toUpperCase()){
                case "U":
                    usage();
                    helpInfo();
                    break;
                case "S":
                    setting();
                    helpInfo();
                    break;
                case "A":
                    about();
                    break;
                case "P":
                    //商品信息保存
                    goodsCenter.store();
                    //订单信息保存
                    orderCenter.storeOrders();
                    break;
                case "Q":
                    quit();
                    break;
                default:
                    helpInfo();
            }
        }
    }

}
