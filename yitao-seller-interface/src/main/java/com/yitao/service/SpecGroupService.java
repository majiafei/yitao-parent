package com.yitao.service;

import com.yitao.domain.SpecGroup;
import com.yitao.dto.SpecGroupDTO;
import com.yitao.vo.SpecGroupVO;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecGroupService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 11:33
 */
public interface SpecGroupService {

    List<SpecGroupVO> querySpecGroupListByCid(Long cid);

    void saveSpecGroup(SpecGroupDTO specGroupDTO);

    void updateSpectGroup(SpecGroupDTO specGroupDTO);

    void deleteSpecGroup(Long specGroupId);

    SpecGroup querySpecGroupByPrimaryKey(Long specGroupId);

}
