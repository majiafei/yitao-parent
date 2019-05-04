package com.yitao.service;

import com.yitao.domain.Brand;
import com.yitao.dto.BrandDTO;
import com.yitao.entiry.PageResult;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: BrandService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/4 11:15
 */
public interface BrandService {

    PageResult<Brand> queryBrandByCondition(BrandDTO brandDTO);

    void saveBrand(Brand brand, List<Long> cids);

}
