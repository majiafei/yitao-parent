package com.yitao.detail.service;

import java.util.Map;

/**
 * @Package: com.yitao.detail.service
 * @ClassName: SpuDetailService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/7/7 11:42
 */
public interface SpuDetailService {

    Map<String, Object> loadModel(Long spuId);

    void creaetStaticHtml(Long spuId);

    void deleteHtml(Long spuId);

}
