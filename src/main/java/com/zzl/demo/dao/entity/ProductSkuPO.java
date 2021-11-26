package com.zzl.demo.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 2C商品库-sku表(ProductSku)实体类
 *
 * @author zhilong
 * @since 2021-04-22 15:28:26
 */

@Data
public class ProductSkuPO implements Serializable {
    private static final long serialVersionUID = -43795988433697710L;
    /**
     * 商品库SKU id
     */
    private Integer id;
    /**
     * product.id
     */
    private Integer pid;
    /**
     * 商品库存
     */
    private Integer qtyNum;
    /**
     * 商品SKU图片
     */
    private String image;
    /**
     * 商品SKU型号
     */
    private String model;
    /**
     * 规格名称
     */
    private String propName;
    /**
     * 规格值
     */
    private String propValue;
    /**
     * 规格关系，留给未来 先颜色还是先尺码的问题
     */
    private Integer propIdx;
    /**
     * 商品SKU编码,物料编码,冗余,不能以此为查询条件,请转为product.id
     */
    private String productCode;
    /**
     * 订单商城 pdt_catalog.id 商品所在类目
     */
    private String catalogId;
    /**
     * 订单商城 pdt_catalog.id 商品所在一级类目
     */
    private String firstCatalogId;
    /**
     * 订单商城 pdt_catalog.id 商品所在二级类目
     */
    private String secondCatalogId;
    /**
     * 是否统一控价 0:否,1:是
     */
    private Integer forcePrice;
    /**
     * 固定价格，统一控制价格
     */
    private Integer fixedPrice;
    /**
     * 统一控价 固定最大价格范围，0:无限制
     */
    private Integer maxPrice;
    /**
     * 统一控价 固定最小价格范围，0:无限制
     */
    private Integer minPrice;
    /**
     * 当前价格，活动价格
     */
    private Integer price;
    /**
     * 零售价，商品原价
     */
    private Integer retailPrice;
    /**
     * 是否删除,0:未删;1:已删
     */
    private Integer deleted;
    private Integer gmtCreate;
    private Integer gmtModified;

}