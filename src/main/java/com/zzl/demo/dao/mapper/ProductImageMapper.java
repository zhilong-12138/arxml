package com.zzl.demo.dao.mapper;

import com.zzl.demo.dao.entity.ProductImagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2C商品库-图片(ProductImagePO)表数据库访问层
 *
 * @author zhilong
 * @since 2021-04-22 15:28:24
 */
@Mapper
public interface ProductImageMapper {

    //==========系统生成公共方法——开始==========

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductImagePO getById(Integer id);

    /**
     * 获取指定Limit的数据lists
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProductImagePO> listByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询lists
     *
     * @param productImagePo 实例对象
     * @return 对象列表
     */
    List<ProductImagePO> listByAll(ProductImagePO productImagePo);

    /**
     * 插入一条记录
     *
     * @param productImagePo 实例对象
     * @return 影响行数
     */
    int insert(ProductImagePO productImagePo);

    /**
     * 根据ID修改数据，禁止非主键更新
     *
     * @param productImagePo 实例对象
     * @return 影响行数
     */
    int updateById(ProductImagePO productImagePo);

    /**
     * 通过主键软删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过主键硬删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int forceDeleteById(Integer id);

    //==========系统生成公共方法——结束==========

    //==========You code here==========
    //...
}