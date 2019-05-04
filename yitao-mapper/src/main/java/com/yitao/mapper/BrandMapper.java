package com.yitao.mapper;

import com.yitao.domain.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ProjectName: house
 * @Package: com.yitao.mapper
 * @ClassName: BrandMapper
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:01
 */
public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand, Long> {
    @Insert("INSERT INTO tb_category_brand(category_id,  brand_id) VALUES(#{cid}, #{brandId})")
    void saveCategoryBrand(@Param("cid") Long cid, @Param("brandId") Long brandId);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{brandId}")
    void deleteCategorBrandByBid(@Param("brandId") Long brandId);
}
