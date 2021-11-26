package com.zzl.demo.dao.mapper;

import com.zzl.demo.dao.entity.ConfigCategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2C商品库-分类表(ConfigCategoryPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-05-15 17:01:10
 */
@Mapper
public interface ConfigCategoryMapper {

    //==========系统生成公共方法——开始==========

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ConfigCategoryPO getById(Integer id);

    /**
     * 获取指定Limit的数据lists
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ConfigCategoryPO> listByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询lists
     *
     * @param configCategoryPo 实例对象
     * @return 对象列表
     */
    List<ConfigCategoryPO> listByAll(ConfigCategoryPO configCategoryPo);

    /**
     * 插入一条记录
     *
     * @param configCategoryPo 实例对象
     * @return 影响行数
     */
    int insert(ConfigCategoryPO configCategoryPo);

    /**
     * 根据ID修改数据，禁止非主键更新
     *
     * @param configCategoryPo 实例对象
     * @return 影响行数
     */
    int updateById(ConfigCategoryPO configCategoryPo);

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
    List<ConfigCategoryPO> getCidByName(String productLine);

    List<ConfigCategoryPO> listCidByNameList(List<String> cidNameList);
}
