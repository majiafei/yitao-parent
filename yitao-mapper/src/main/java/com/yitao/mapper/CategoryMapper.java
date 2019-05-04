package com.yitao.mapper;

import com.yitao.domain.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.mapper
 * @ClassName: CategoryMapper
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:04
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

    @Select("SELECT c.id, c.`name` FROM tb_category c INNER JOIN tb_category_brand cb on c.id = cb.category_id WHERE cb.brand_id = #{bid}")
    List<Category> queryCategoryListByBid(@Param("bid") Long bid);

}
