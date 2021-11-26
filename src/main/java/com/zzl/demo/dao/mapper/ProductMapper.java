package com.zzl.demo.dao.mapper;

import com.zzl.demo.dao.entity.ProductPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2C商品库(ProductPO)表数据库访问层
 *
 * @author zhilong
 * @since 2021-04-22 15:28:20
 */
@Mapper
public interface ProductMapper {

    //==========系统生成公共方法——开始==========

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductPO getById(Integer id);

    /**
     * 获取指定Limit的数据lists
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProductPO> listByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询lists
     *
     * @param productPo 实例对象
     * @return 对象列表
     */
    List<ProductPO> listByAll(ProductPO productPo);

    /**
     * 插入一条记录
     *
     * @param productPo 实例对象
     * @return 影响行数
     */
    int insert(ProductPO productPo);

    /**
     * 根据ID修改数据，禁止非主键更新
     *
     * @param productPo 实例对象
     * @return 影响行数
     */
    int updateById(ProductPO productPo);

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