package com.yitao.service;

import com.yitao.domain.SpecParam;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecParamService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 12:26
 */

public interface SpecParamService {

    List<SpecParam> querySpecPramListByGid(Long groupId);

    void saveSpecParam(SpecParam specParam);

    void updateSpecParam(SpecParam specParam);

    void deleteSpecParam(Long specParamId);

}
