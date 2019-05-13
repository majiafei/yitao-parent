package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.SpecParam;
import com.yitao.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecPramServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 12:26
 */

@Component
@Service
public class SpecPramServiceImpl implements SpecParamService{

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecParam> querySpecPramListByGid(Long groupId) {
        Example example = new Example(SpecParam.class);
        example.createCriteria().andEqualTo("specGroupId", groupId);
        return specParamMapper.selectByExample(example);
    }

    @Override
    public List<SpecParam> querySpecParamListByCondition(SpecParam specParam) {
        Example example = new Example(SpecParam.class);
        Example.Criteria criteria = example.createCriteria();
        if (specParam.getCategoryId() != null) {
            criteria.andEqualTo("categoryId", specParam.getCategoryId());
        }
        if (specParam.getSpecGroupId() != null) {
            criteria.andEqualTo("specGroupId", specParam.getSpecGroupId());
        }
        return specParamMapper.selectByExample(example);
    }

    @Override
    public void saveSpecParam(SpecParam specParam) {
        int insert = specParamMapper.insert(specParam);
        if (insert == 0) {
            throw new ServiceException("新增参数失败");
        }
    }

    @Override
    public void updateSpecParam(SpecParam specParam) {
        int i = specParamMapper.updateByPrimaryKeySelective(specParam);
        if (i == 0) {
            throw new ServiceException("修改参数失败");
        }
    }

    @Override
    public void deleteSpecParam(Long specParamId) {
        int i = specParamMapper.deleteByPrimaryKey(specParamId);
        if (i == 0) {
            throw new ServiceException("删除参数失败");
        }
    }

}
