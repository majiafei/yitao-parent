package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yitao.domain.Brand;
import com.yitao.dto.BrandDTO;
import com.yitao.entiry.PageResult;
import com.yitao.mapper.BrandMapper;
import com.yitao.vo.BrandVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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

    /**
     * 根据条件查询品牌
     * @param brandDTO
     * @return
     */
    @Override
    public PageResult<BrandVO> queryBrandByCondition(BrandDTO brandDTO) {
        // 开启分页
        PageHelper.startPage(brandDTO.getPage(), brandDTO.getRows());

        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(brandDTO.getKey())) {
            example.createCriteria().andLike("brandName", "%" + brandDTO.getKey() + "%").orEqualTo("brandLetter", brandDTO.getKey());
        }

        // 排序
        if (StringUtils.isNotBlank(brandDTO.getSortBy())) {
            example.setOrderByClause(brandDTO.getSortBy() + (brandDTO.getDesc() ? " desc" : " asc"));
        }

        List<Brand> brandList = brandMapper.selectByExample(example);

        List<BrandVO> brandVOList = new ArrayList<>();

        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        brandList.forEach(brand -> {
            BrandVO brandVO = new BrandVO();
            brandVO.setName(brand.getBrandName());
            brandVO.setId(brand.getBranId());
            brandVO.setImage(brand.getBrandImage());
            brandVO.setLetter(brand.getBrandLetter());
            brandVOList.add(brandVO);
        });

        return PageResult.build(pageInfo.getTotal(), brandVOList);
    }

    /**
     * 新增品牌
     * @param brand 品牌
     * @param cids 分类id
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);

        for (int i = 0; i < cids.size(); i++) {
            brandMapper.saveCategoryBrand(cids.get(i), brand.getBranId());
        }
    }

    /**
     * 修改品牌信息
     * @param brand
     * @param cids
     */
    @Override
    public void updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKey(brand);

        // 删除中间表的数据，然后再插入
        brandMapper.deleteCategorBrandByBid(brand.getBranId());

        for (int i = 0; i < cids.size(); i++) {
            brandMapper.saveCategoryBrand(cids.get(i), brand.getBranId());
        }
    }

    /**
     * 删除品牌
     * @param brandId
     */
    @Override
    public void deleteBrand(Long brandId) {
        brandMapper.deleteByPrimaryKey(brandId);

        // 删除中间表的数据
        brandMapper.deleteCategorBrandByBid(brandId);
    }

    @Override
    public List<BrandVO> queryBrandListByCid(Long cid) {
        List<Brand> brandList = brandMapper.queryBrandListByCid(cid);

        BrandVO brandVO = null;
        List<BrandVO> brandVOList = new ArrayList<>();
        for (Brand brand : brandList) {
            brandVO = new BrandVO();
            brandVO.setImage(brand.getBrandImage());
            brandVO.setLetter(brand.getBrandLetter());
            brandVO.setId(brand.getBranId());
            brandVO.setName(brand.getBrandName());
            brandVOList.add(brandVO);
        }
        return brandVOList;
    }

    @Override
    public BrandVO getBrandById(Long bid) {
        Brand brand = brandMapper.selectByPrimaryKey(bid);
        BrandVO brandVO = new BrandVO();
        brandVO.setLetter(brand.getBrandLetter());
        brandVO.setImage(brand.getBrandImage());
        brandVO.setId(brand.getBranId());
        brandVO.setName(brand.getBrandName());

        return brandVO;
    }


}
