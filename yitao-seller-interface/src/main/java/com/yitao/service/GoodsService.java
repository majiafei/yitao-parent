package com.yitao.service;

import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.vo.SpuVO;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: GoodsService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:13
 */

public interface GoodsService {

    PageResult<SpuVO> querySpuListByCondition(SpuDTO spuDTO);

}
