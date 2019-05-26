package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.SpecGroup;
import com.yitao.dto.SpecGroupDTO;
import com.yitao.mapper.SpecGroupMapper;
import com.yitao.vo.SpecGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<SpecGroupVO> querySpecGroupListByCid(Long cid) {
        Example example = new Example(SpecGroup.class);
        example.createCriteria().andEqualTo("categoryId", cid);

        List<SpecGroup> specGroupList = specGroupMapper.selectByExample(example);
        List<SpecGroupVO> specGroupVOList= specGroupList.stream().
                                        map(specGroup -> {
                                                SpecGroupVO specGroupVO = new SpecGroupVO();
                                                specGroupVO.setId(specGroup.getSpectGroupId());
                                                specGroupVO.setName(specGroup.getSpectGroupName());
                                                return specGroupVO;
                                            }).collect(Collectors.toList());
        return specGroupVOList;
    }

    @Override
    public void saveSpecGroup(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = buildSpecGroup(specGroupDTO);

        Example example = new Example(SpecGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spectGroupName", specGroup.getSpectGroupName());
        criteria.andEqualTo("categoryId", specGroup.getCategoryId());
        // 根据名称和分类id查询
        List<SpecGroup> specGroupList = specGroupMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(specGroupList)) {
            throw new ServiceException("同分类下的组名称不能重复");
        }

        int insert = specGroupMapper.insert(specGroup);
        if (insert == 0) {
            throw new ServiceException("新增分组失败");
        }
    }

    @Override
    public void updateSpectGroup(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = buildSpecGroup(specGroupDTO);
        int i = specGroupMapper.updateByPrimaryKeySelective(specGroup);
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

    private SpecGroup buildSpecGroup(SpecGroupDTO specGroupDTO) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setSpectGroupId(specGroupDTO.getId());
        specGroup.setCategoryId(specGroupDTO.getCid());
        specGroup.setSpectGroupName(specGroupDTO.getName());

        return specGroup;
    }

}
