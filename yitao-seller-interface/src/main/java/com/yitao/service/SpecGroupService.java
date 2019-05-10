package com.yitao.service;

import com.yitao.domain.SpecGroup;

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

    List<SpecGroup> querySpecGroupListByCid(Long cid);

    void saveSpecGroup(SpecGroup specGroup);

    void updateSpectGroup(SpecGroup specGroup);

    void deleteSpecGroup(Long specGroupId);

    SpecGroup querySpecGroupByPrimaryKey(Long specGroupId);

}
