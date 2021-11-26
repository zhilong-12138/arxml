package com.zzl.demo.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 2C商品库-分类表(ConfigCategory)实体类
 *
 * @author makejava
 * @since 2021-05-15 17:01:11
 */

@Data
public class ConfigCategoryPO implements Serializable {
    private static final long serialVersionUID = 321045971824102229L;
    /**
     * 类目id cid
     */
    private Integer id;
    /**
     * 分类标题
     */
    private String title;
    /**
     * 所属品牌ID
     */
    private Integer brandId;
    /**
     * 一级cid config_category.id
     */
    private Integer firstCid;
    /**
     * 二级cid config_category.id
     */
    private Integer secondCid;
    /**
     * 父类cid config_category.id
     */
    private Integer parentCid;
    /**
     * 当前类目的级别,最多3级
     */
    private Integer level;
    /**
     * 排序ID
     */
    private Integer sort;
    /**
     * 是否删除,0:未删;1:已删
     */
    private Integer deleted;
    private Integer gmtCreate;
    private Integer gmtModified;


}
