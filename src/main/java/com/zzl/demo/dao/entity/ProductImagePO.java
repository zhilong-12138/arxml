package com.zzl.demo.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 2C商品库-图片(ProductImage)实体类
 *
 * @author zhilong
 * @since 2021-04-22 15:28:24
 */

@Data
public class ProductImagePO implements Serializable {
    private static final long serialVersionUID = -15787178402083380L;
    /**
     * product.id
     */
    private Integer id;
    /**
     * 商品图片地址，以“,”分隔，最大6张：128*6+5=773，建议单张图片长度不超过128个字符串
     */
    private String goodsImages;
    /**
     * PC-视频缩略图
     */
    private String pcVideoImg;
    /**
     * PC-视频播放地址
     */
    private String pcVideoUrl;
    /**
     * H5-视频缩略图
     */
    private String h5VideoImg;
    /**
     * H5-视频播放地址
     */
    private String h5VideoUrl;
    /**
     * 是否删除,0:未删;1:已删
     */
    private Integer deleted;
    private Integer gmtCreate;
    private Integer gmtModified;

}