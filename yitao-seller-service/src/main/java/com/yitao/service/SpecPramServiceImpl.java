package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.SpecParam;
import com.yitao.dto.SpecParamDTO;
import com.yitao.mapper.SpecParamMapper;
import com.yitao.vo.SpecParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
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
    public List<SpecParamVO> querySpecPramListByCidAndGid(Long cid, Long groupId) {
        Example example = new Example(SpecParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId", cid);
        criteria.andEqualTo("specGroupId", groupId);

        List<SpecParam> specParamList = specParamMapper.selectByExample(example);

        List<SpecParamVO> specParamVOList = new ArrayList<>();
        specParamList.forEach(specParam -> {
            SpecParamVO specParamVO = new SpecParamVO();
            specParamVO.setGeneric(specParam.getGeneric());
            specParamVO.setId(specParam.getSpecParamId());
            specParamVO.setName(specParam.getSpecParamName());
            specParamVO.setUnit(specParam.getUnit());
            specParamVOList.add(specParamVO);
        });

        return specParamVOList;
    }

    @Override
    public List<SpecParamVO> querySpecParamListByCondition(SpecParamDTO specParamDTO) {
        Example example = new Example(SpecParam.class);
        Example.Criteria criteria = example.createCriteria();
        if (specParamDTO.getCid() != null) {
            criteria.andEqualTo("categoryId", specParamDTO.getCid());
        }
        if (specParamDTO.getGid() != null) {
            criteria.andEqualTo("specGroupId", specParamDTO.getGid());
        }

        List<SpecParam> specParamList = specParamMapper.selectByExample(example);
        List<SpecParamVO> specParamVOList = new ArrayList<>();
        specParamList.forEach(specParam -> {
            SpecParamVO specParamVO = new SpecParamVO();
            specParamVO.setGeneric(specParam.getGeneric());
            specParamVO.setId(specParam.getSpecParamId());
            specParamVO.setName(specParam.getSpecParamName());
            specParamVO.setUnit(specParam.getUnit());
            specParamVOList.add(specParamVO);
        });
        return specParamVOList;
    }

    @Override
    public void saveSpecParam(SpecParamDTO specParamDTO) {
        SpecParam specParam = new SpecParam();
        specParam.setCategoryId(specParamDTO.getCid());
        specParam.setGeneric(specParamDTO.getGeneric() ? Byte.valueOf(1 + "") : Byte.valueOf(0 + ""));
        specParam.setNumeric(specParamDTO.getNumeric() ? Byte.valueOf(1 + "") : Byte.valueOf(0 +""));
        specParam.setSearching(specParamDTO.getSearching() ? Byte.valueOf(1 + "") : Byte.valueOf(0 + ""));
        specParam.setSegments(specParamDTO.getSegments());
        specParam.setSpecParamName(specParamDTO.getName());
        specParam.setSpecGroupId(specParamDTO.getGroupId());
        specParam.setUnit(specParamDTO.getUnit());

        int insert = specParamMapper.insertSelective(specParam);
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
