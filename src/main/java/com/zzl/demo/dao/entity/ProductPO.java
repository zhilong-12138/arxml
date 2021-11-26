package com.zzl.demo.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 2C商品库(Product)实体类
 *
 * @author zhilong
 * @since 2021-04-22 15:28:21
 */

@Data
public class ProductPO implements Serializable {
    private static final long serialVersionUID = -52862328577347577L;
    /**
     * 2C全局统一商品库ID
     */
    private Integer id;
    /**
     * 创建人ID
     */
    private Integer uid;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 商品副标题
     */
    private String subtitle;
    /**
     * 商品英文标题
     */
    private String enTitle;
    /**
     * 商品主图
     */
    private String image;
    /**
     * 商品型号
     */
    private String model;
    /**
     * 商品标签
     */
    private String tags;
    /**
     * 品牌名称ID,config_brand.id
     */
    private Integer brandId;
    /**
     * 一级类目ID,config_category.id
     */
    private Integer firstCid;
    /**
     * 二级类目ID,config_category.id
     */
    private Integer secondCid;
    /**
     * 当前类目ID,config_category.id
     */
    private Integer cid;
    /**
     * 排序序列
     */
    private Integer sort;
    /**
     * 该商品下SKU最大价格
     */
    private Integer maxPrice;
    /**
     * 该商品下SKU最小价格
     */
    private Integer minPrice;
    /**
     * 商品状态 0:待提交,1:待审核,2:审核通过,3:审核拒绝,4:已下架,5:已上架,
     */
    private Integer productStatus;
    /**
     * 分配经销商数量
     */
    private Integer countAllocateDealer;
    /**
     * 分配门店数量
     */
    private Integer countAllocateStore;
    /**
     * 是否删除,0:未删;1:已删
     */
    private Integer deleted;
    private Integer gmtCreate;
    private Integer gmtModified;

}