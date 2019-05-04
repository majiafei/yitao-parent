package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.domain.Category;
import com.yitao.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
}
