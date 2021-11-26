package com.zzl.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhiLong
 * @version 1.0
 * @date 2021/4/22 15:07
 */
@Data
public class ProductBasePO implements Serializable {

    private String productCode;

    private String model;

    private String brandName;

    private String price;

    private String imageOne;

    private String imageTwo;

    private String imageThree;

    private String pcDetail;

    private String productLine;
}
