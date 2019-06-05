package com.yitao.service;

import com.yitao.domain.Spu;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpuDetailVO;
import com.yitao.vo.SpuVO;

import java.rmi.server.ServerCloneException;
import java.util.List;

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

    void saveGoods(SpuDTO spuDTO);

    SpuDetailVO getSpuDetailBySpuId(Long spuId);

    List<SkuVO> getSkuListBySpuId(Long spuId);

    void deleteGoodsById(Long spuId);

    /**
     * 上下架产品
     * @param spuDTO
     */
    void saleableSpu(SpuDTO spuDTO);

    Spu getSpuById(Long spuId);

}
