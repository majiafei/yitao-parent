package com.yitao.service;

import com.yitao.domain.SpecParam;
import com.yitao.dto.SpecParamDTO;
import com.yitao.vo.SpecParamVO;

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

    List<SpecParamVO> querySpecPramListByCidAndGid(Long cid, Long groupId);

    List<SpecParamVO> querySpecParamListByCondition(SpecParamDTO specParamDTO);

    void saveSpecParam(SpecParamDTO specParamDTO);

    void updateSpecParam(SpecParam specParam);

    void deleteSpecParam(Long specParamId);

}
