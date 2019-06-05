package com.yitao.service;

import com.yitao.domain.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.rmi.server.ServerCloneException;
import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: CategoryService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 18:50
 */
public interface CategoryService {

    List<Category> queryCategoryListByBid(Long bid);

    List<Category> queryCategoryListByPid(Long parentId);

    void saveCatetory(Category category);

    void updateCategory(Category category);

    void deleteCategoryById(Long categoryId);

    Category selectByPrimaryKey(Long categoryId);

    List<Category> listByIds(List<Long> ids);

}
