package com.zzl.demo.dao.mapper;

import com.zzl.demo.dao.entity.ProductDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2C商品库-详情(ProductDetailPO)表数据库访问层
 *
 * @author zhilong
 * @since 2021-04-22 15:28:22
 */
@Mapper
public interface ProductDetailMapper {

    //==========系统生成公共方法——开始==========

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductDetailPO getById(Integer id);

    /**
     * 获取指定Limit的数据lists
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProductDetailPO> listByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询lists
     *
     * @param productDetailPo 实例对象
     * @return 对象列表
     */
    List<ProductDetailPO> listByAll(ProductDetailPO productDetailPo);

    /**
     * 插入一条记录
     *
     * @param productDetailPo 实例对象
     * @return 影响行数
     */
    int insert(ProductDetailPO productDetailPo);

    /**
     * 根据ID修改数据，禁止非主键更新
     *
     * @param productDetailPo 实例对象
     * @return 影响行数
     */
    int updateById(ProductDetailPO productDetailPo);

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