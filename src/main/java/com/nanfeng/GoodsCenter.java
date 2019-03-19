package com.nanfeng;

/**
 * Author：nanfeng
 * Created:2019/3/9
 */
public interface GoodsCenter {

    //添加商品
    void addGoods(Goods goods);

    //移除商品
    void removeGoods(String goodsId);

    //更新商品
    void updateGoods(Goods goods);

    //判断商品是否存在
    boolean isExitGoods(String goodsId);

    //获取商品
    Goods getGoods(String goodsId);

    //列举所有商品
    String listGoods();

    //存储商品信息
    void store();

    //加载商品信息
    void load();

}
