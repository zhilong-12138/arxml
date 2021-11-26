package com.zzl.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhilong
 * @version 1.0
 * @date 2021/6/1 下午2:16
 */
@Data
public class ProductExcelInsertQuery implements Serializable {
    private static final long serialVersionUID = 6880542753947052342L;


    /**
     *类目名称
     */
    private String categoryName;

    /**
     *商品名称
     */
    private String title;

    /**
     *商品简介
     */
    private String subtitle;

    /**
     *商品型号
     */
    private String model;

    /**
     *物料编码
     */
    private String productCode;

    /**
     *商品图片（逗号分割）
     */
    private String image;

    /**
     *商品规格
     */
    private String propValue;

    /**
     *商品详情图片
     */
    private String productDetail;

}
