package com.nanfeng.impl;

import com.nanfeng.Goods;
import com.nanfeng.GoodsCenter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public class SimpleGoodsCenter implements GoodsCenter {

    //用一个Map专门来管理商品《商品编号，商品对象》
    private final Map<String,Goods> goodsMap = new HashMap<>();

    //当前存储在文件中
    private String filePath = System.getProperty("user.dir") + File.separator + "goods.txt";

    @Override
    public void addGoods(Goods goods) {
        this.goodsMap.put(goods.getId(),goods);
    }

    @Override
    public void removeGoods(String goodsId) {
        this.goodsMap.remove(goodsId);
    }

    @Override
    public void updateGoods(Goods goods) {
        if(this.goodsMap.containsKey(goods.getId())){
            this.goodsMap.put(goods.getId(),goods);
        }
    }

    @Override
    public boolean isExitGoods(String goodsId) {
        return this.goodsMap.containsKey(goodsId);
    }

    @Override
    public Goods getGoods(String goodsId) {
        return this.goodsMap.get(goodsId);
    }

    @Override
    public String listGoods() {
        //专门用来放字符串
        StringBuilder sb = new StringBuilder();
        sb.append("*************商品信息******************\n");
        sb.append("\t\t编号\t\t名称\t\t单价\n");
        for(Map.Entry<String,Goods> entry : this.goodsMap.entrySet()){
            Goods goods = entry.getValue();
            sb.append(String.format("\t\t%s\t\t\t%s\t\t%.2f\n",
                    goods.getId(),goods.getName(),goods.getPrice()));
        }
        sb.append("***************************************\n");
        return sb.toString();
    }

    @Override
    public void store() {
        //1:华为:2222
        File file = new File(filePath);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(Map.Entry<String,Goods> entry : this.goodsMap.entrySet()){
                Goods goods = entry.getValue();
                writer.write(String.format("%s:%s:%.2f\n",
                        goods.getId(),goods.getName(),goods.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        File file = new File(filePath);
        if(file.exists() && file.isFile()){
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                while((line = reader.readLine())!=null){
                    String[] values = line.split(":");
                    if(values.length==3){
                        Goods goods = new Goods(
                                values[0],
                                values[1],
                                Double.parseDouble(values[2]));
                        this.addGoods(goods);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        GoodsCenter goodsCenter = new SimpleGoodsCenter();
////        Goods goods = new Goods("1","华为",1999D);
////       goodsCenter.addGoods(goods);
//        goodsCenter.load();
//        System.out.println(goodsCenter.listGoods());
////        goodsCenter.store();
//    }

}
