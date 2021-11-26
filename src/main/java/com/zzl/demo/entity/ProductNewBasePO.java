package com.zzl.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhiLong
 * @version 1.0
 * @date 2021/4/22 15:07
 */
@Data
public class ProductNewBasePO implements Serializable {

    private String productLine;

    private String title;

    private String subtitle;

    private String model;

    private String productCode;

    private String image;

    private String propValue;

}
