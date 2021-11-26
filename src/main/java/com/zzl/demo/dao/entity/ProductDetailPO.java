package com.zzl.demo.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 2C商品库-详情(ProductDetail)实体类
 *
 * @author zhilong
 * @since 2021-04-22 15:28:23
 */

@Data
public class ProductDetailPO implements Serializable {
    private static final long serialVersionUID = -50729867927545768L;
    /**
     * product.id
     */
    private Integer id;
    /**
     * PC-商品详情 65535 字
     */
    private String pcDetail;
    /**
     * h5-商品详情 65535 字
     */
    private String h5Detail;
    /**
     * 商品售后
     */
    private String afterSaleDetail;
    /**
     * 是否删除,0:未删;1:已删
     */
    private Integer deleted;
    private Integer gmtCreate;
    private Integer gmtModified;

}