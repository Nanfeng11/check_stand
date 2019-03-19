package com.nanfeng;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public class Order {

    //订单编号
    private final String orderId;
    //订单Order创建完成后，goodsInfo属性实例化HashMap
                    //商品编号  商品数量
    private final Map<String,Integer> goodsInfo = new HashMap<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getOrderInfo() {
        return this.goodsInfo;
    }

    //添加商品以及数量
    public void add(String goodsId,Integer count){
        int sum;
        if(goodsInfo.containsKey(goodsId)){
            sum = goodsInfo.get(goodsId) + count;
        }else{
            sum = count;
        }
        this.goodsInfo.put(goodsId,count);
    }

    //取消商品以及数量
    public void cancel(String goodsId,Integer count){
        if(this.goodsInfo.containsKey(goodsId)){
            int sum = this.goodsInfo.get(goodsId);
            sum =  sum - count;
            sum = sum<0?0:sum;
            if(sum==0){
                this.goodsInfo.remove(goodsId);
            }else{
                this.goodsInfo.put(goodsId,sum);
            }
        }
    }

    //清空订单商品
    public void clear(){
        this.goodsInfo.clear();
    }
}
