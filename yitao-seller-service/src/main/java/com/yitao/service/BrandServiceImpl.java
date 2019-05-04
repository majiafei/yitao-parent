package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yitao.domain.Brand;
import com.yitao.dto.BrandDTO;
import com.yitao.entiry.PageResult;
import com.yitao.mapper.BrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: BrandService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 12:42
 */

@Component
@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByCondition(BrandDTO brandDTO) {
        // 开启分页
        PageHelper.startPage(brandDTO.getPage(), brandDTO.getRows());

        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(brandDTO.getKey())) {
            example.createCriteria().andLike("name", brandDTO.getKey()).orEqualTo("letter", brandDTO.getKey());
        }

        // 排序
        if (StringUtils.isNotBlank(brandDTO.getSortBy())) {
            example.setOrderByClause(brandDTO.getSortBy() + (brandDTO.getDesc() ? "desc" : "asc"));
        }

        List<Brand> brandList = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        return PageResult.build(pageInfo.getTotal(), brandList);
    }

    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);

        for (int i = 0; i < cids.size(); i++) {
            brandMapper.saveCategoryBrand(cids.get(i), brand.getBranId());
        }
    }
}
