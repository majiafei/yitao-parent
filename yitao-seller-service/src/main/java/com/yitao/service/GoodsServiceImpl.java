package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yitao.domain.Brand;
import com.yitao.domain.Spu;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.mapper.BrandMapper;
import com.yitao.mapper.CategoryMapper;
import com.yitao.mapper.SpuMapper;
import com.yitao.vo.SpuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: GoodsServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:20
 */

@Component
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<SpuVO> querySpuListByCondition(SpuDTO spuDTO) {
        // 分页
        PageHelper.startPage(spuDTO.getPage(), spuDTO.getRows());

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(spuDTO.getKey())) {
            criteria.andLike("title", "%" + spuDTO.getKey() + "%");
        }

        if (spuDTO.getSaleable() != null) {
            criteria.andEqualTo("saleable", spuDTO.getSaleable());
        }

        criteria.andEqualTo("valid", 1);
        // 排序
        example.setOrderByClause("last_update_time desc");

        List<Spu> spuList = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spuList);

        return PageResult.build(pageInfo.getTotal(), hanleSpuTOSpuDTO(spuList));
    }

    private List<SpuVO> hanleSpuTOSpuDTO(List<Spu> spuList) {
        List<SpuVO> spuVOList = new ArrayList<>();
        SpuVO spuVO = null;

        for (Spu spu : spuList) {
            spuVO = new SpuVO();
            BeanUtils.copyProperties(spu, spuVO);
            spuVO.setId(spu.getSpuId());
            // 查询类目的名称
            List<String> categoryNameList = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList());
            spuVO.setCname(StringUtils.join(categoryNameList, "/"));

            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuVO.setBname(brand == null ? "" : brand.getBrandName());

            spuVOList.add(spuVO);
        }

        return spuVOList;
    }
}
