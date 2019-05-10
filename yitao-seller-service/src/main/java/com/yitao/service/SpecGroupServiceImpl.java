package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.SpecGroup;
import com.yitao.mapper.SpecGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecGroupServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 11:34
 */

@Component
@Service
public class SpecGroupServiceImpl implements SpecGroupService{

    @Autowired
    private SpecGroupMapper specGroupMapper;

    /**
     * 查询某一分类下的规格组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> querySpecGroupListByCid(Long cid) {
        Example example = new Example(SpecGroup.class);
        example.createCriteria().andEqualTo("categoryId", cid);
        return specGroupMapper.selectByExample(example);
    }

    @Override
    public void saveSpecGroup(SpecGroup specGroup) {
        int insert = specGroupMapper.insert(specGroup);
        if (insert == 0) {
            throw new ServiceException("新增分组失败");
        }
    }

    @Override
    public void updateSpectGroup(SpecGroup specGroup) {
        int i = specGroupMapper.updateByPrimaryKey(specGroup);
        if (i == 0) {
            throw new ServiceException("修改分组失败");
        }
    }

    @Override
    public void deleteSpecGroup(Long specGroupId) {
        int i = specGroupMapper.deleteByPrimaryKey(specGroupId);
        if (i == 0) {
            throw new ServiceException("删除分组失败");
        }
    }

    @Override
    public SpecGroup querySpecGroupByPrimaryKey(Long specGroupId) {
        return specGroupMapper.selectByPrimaryKey(specGroupId);
    }
}
