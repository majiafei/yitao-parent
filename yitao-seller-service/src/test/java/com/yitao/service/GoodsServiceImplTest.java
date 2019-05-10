package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.vo.SpuVO;
import org.junit.Test;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: GoodsServiceImplTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:40
 */
public class GoodsServiceImplTest extends YitaoSellerServiceApplicationTests {

    @Reference
    private GoodsService goodsService;

    @Test
    public void testList() {
        SpuDTO spuDTO = new SpuDTO();
        spuDTO.setPage(0);
        spuDTO.setRows(10);
        PageResult<SpuVO> spuVOPageResult = goodsService.querySpuListByCondition(spuDTO);
        System.out.println(spuVOPageResult.getTotal());
    }

}
