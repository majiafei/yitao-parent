package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.Category;
import com.yitao.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.rmi.server.ServerCloneException;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: CategoryServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 18:51
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryListByBid(Long bid) {
        return categoryMapper.queryCategoryListByBid(bid);
    }

    /**
     * 查找某节点的子节点
     * @param parentId 父节点id
     * @return
     */
    @Override
    public List<Category> queryCategoryListByPid(Long parentId) {
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId", parentId);
        return categoryMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void saveCatetory(Category category)  {
        int insert = categoryMapper.insert(category);
        if (insert == 0) {
            throw new ServiceException("保存分类失败");
        }
    }

    @Override
    @Transactional
    public void updateCategory(Category category) {
        int i = categoryMapper.updateByPrimaryKey(category);
        if (i == 0) {
            throw new ServiceException("更新分类失败");
        }
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        int i = categoryMapper.deleteByPrimaryKey(categoryId);
        if (i == 0) {
             throw new ServiceException("删除分类失败");
        }
    }

    @Override
    public Category selectByPrimaryKey(Long categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<Category> listByIds(List<Long> ids) {
        return categoryMapper.selectByIdList(ids);
    }

    @Override
    public List<Category> queryAllByCid3(Long id) {
        Category c3 = categoryMapper.selectByPrimaryKey(id);
        Category c2 = categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = categoryMapper.selectByPrimaryKey(c2.getParentId());
        List<Category> list = Arrays.asList(c1, c2, c3);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("分类不存在");
        }
        return list;
    }


}
