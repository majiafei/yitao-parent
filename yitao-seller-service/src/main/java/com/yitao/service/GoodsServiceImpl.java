package com.yitao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yitao.common.enums.GoodsStatusEnum;
import com.yitao.common.exception.ServiceException;
import com.yitao.domain.*;
import com.yitao.dto.SkuDTO;
import com.yitao.dto.SpecDetailDTO;
import com.yitao.dto.SpuDTO;
import com.yitao.entiry.PageResult;
import com.yitao.mapper.*;
import com.yitao.vo.SkuVO;
import com.yitao.vo.SpuDetailVO;
import com.yitao.vo.SpuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.lang.model.element.VariableElement;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *     商品服务
 * </p>
 * @Package: com.yitao.service
 * @ClassName: GoodsServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:20
 */

@Component
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public PageResult<SpuVO> querySpuListByCondition(SpuDTO spuDTO) {
        // 分页
        PageHelper.startPage(spuDTO.getPage(), spuDTO.getRows());

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(spuDTO.getKey())) {
            criteria.andLike("title", "%" + spuDTO.getKey() + "%");
        }

        if (spuDTO.getSaleable() != null) {
            criteria.andEqualTo("saleable", spuDTO.getSaleable());
        }

        criteria.andEqualTo("valid", 1);
        // 排序
        example.setOrderByClause("last_update_time desc");

        List<Spu> spuList = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spuList);

        return PageResult.build(pageInfo.getTotal(), hanleSpuTOSpuDTO(spuList));
    }

    @Transactional
    @Override
    public void saveGoods(SpuDTO spuDTO) {
        Spu spu = buildSpu(spuDTO);
        // 添加spu
        int insert = spuMapper.insertSelective(spu);
        if (insert == 0) {
            throw new ServiceException("insert goods failed");
        }

        SpecDetailDTO specDetailDTO = spuDTO.getSpuDetail();
        SpuDetail spuDetail = buildSpuDetail(specDetailDTO);
        spuDetail.setSpuId(spu.getSpuId());
        // 添加商品详情
        int spuDetailInsert = spuDetailMapper.insertSelective(spuDetail);
        if (spuDetailInsert == 0) {
            throw new ServiceException("insert spudetail failed");
        }

        List<SkuDTO> skuDTOList = spuDTO.getSkus();
        skuDTOList.forEach(skuDTO -> {
            Sku sku = new Sku();
            sku.setSpuId(spu.getSpuId());
            sku.setTitle(skuDTO.getTitle());
            sku.setCreateTime(new Date());
            sku.setImages(skuDTO.getImages());
            sku.setIndexes(skuDTO.getIndexes());
            sku.setOwnSpec(skuDTO.getOwnSpec());
            sku.setPrice(skuDTO.getPrice());
            sku.setLastUpdateTime(new Date());
            int skuInsert = skuMapper.insertSelective(sku);
            if (skuInsert == 0) {
                throw new ServiceException("insert sku faield");
            }

            Stock stock = new Stock();
            stock.setStock(skuDTO.getStock());
            stock.setSkuId(sku.getSkuId());
            // 保存库存
            int stockInsert = stockMapper.insertSelective(stock);
            if (stockInsert == 0) {
                throw new ServiceException("insert stock failed");
            }
        });

    }

    @Override
    public SpuDetailVO getSpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);

        SpuDetailVO spuDetailVO = new SpuDetailVO();
        BeanUtils.copyProperties(spuDetail, spuDetailVO);

        return spuDetailVO;
    }

    @Override
    public List<SkuVO> getSkuListBySpuId(Long spuId) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        List<Sku> skuList = skuMapper.selectByExample(example);

        List<SkuVO> skuVOList = new ArrayList<>();
        skuList.forEach(sku -> {
            SkuVO skuVO = new SkuVO();
            BeanUtils.copyProperties(sku, skuVO);
            skuVO.setId(sku.getSkuId());

            Stock stock = stockMapper.selectByPrimaryKey(sku.getSkuId());
            skuVO.setStock(stock.getStock());

            skuVOList.add(skuVO);
        });

        return skuVOList;
    }

    @Transactional
    @Override
    public void deleteGoodsById(Long spuId) {
        // 删除spu
        int deleteSpu = spuMapper.deleteByPrimaryKey(spuId);
        if (deleteSpu == 0) {
            throw new ServiceException("删除spu失败");
        }
        // 删除sku
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        int deleteSku = skuMapper.deleteByExample(example);
        if (deleteSku == 0) {
            throw new ServiceException("删除sku失败");
        }
        // 删除详情
        int deleteDetail = spuDetailMapper.deleteByPrimaryKey(spuId);
        if (deleteDetail == 0) {
            throw new ServiceException("删除详情失败");
        }
    }

    @Override
    public void saleableSpu(SpuDTO spuDTO) {
        Spu spu = spuMapper.selectByPrimaryKey(spuDTO.getId());
        if (spu == null) {
            throw new ServiceException("该商品不存在");
        }


        boolean saleable = (spu.getSaleable() == 0x00) ? false : true;
        if (saleable != spuDTO.getSaleable()) {
            throw new ServiceException("该商品的状态已被修改");
        }

        if (spuDTO.getSaleable()) {
            spu.setSaleable(Byte.valueOf(GoodsStatusEnum.GOODS_UNDERCARRIAGE_STATUS.getCode() + ""));
        } else {
            spu.setSaleable(Byte.valueOf(GoodsStatusEnum.GOODS_SHELF_STATUS.getCode() + ""));
        }

        int i = spuMapper.updateByPrimaryKey(spu);
        if (i == 0) {
            throw new ServiceException("商品上架或者下架失败");
        }
    }

    @Override
    public Spu getSpuById(Long spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }

    private SpuDetail buildSpuDetail(SpecDetailDTO specDetailDTO) {
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setAfterService(specDetailDTO.getAfterService());
        spuDetail.setDescription(specDetailDTO.getDescription());
        spuDetail.setGenericSpec(specDetailDTO.getGenericSpec());
        spuDetail.setSpecialSpec(specDetailDTO.getSpecialSpec());

        return spuDetail;
    }

    private Spu buildSpu(SpuDTO spuDTO) {
        Spu spu = new Spu();
        spu.setBrandId(spuDTO.getBrandId());
        spu.setCid1(spuDTO.getCid1());
        spu.setCid2(spuDTO.getCid2());
        spu.setCid3(spuDTO.getCid3());
        // 上架
        spu.setSaleable(Byte.valueOf(GoodsStatusEnum.GOODS_SHELF_STATUS.getCode() + ""));
        spu.setTitle(spuDTO.getTitle());
        spu.setSubTitle(spuDTO.getSubTitle());
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());

        return spu;
    }

    private List<SpuVO> hanleSpuTOSpuDTO(List<Spu> spuList) {
        List<SpuVO> spuVOList = new ArrayList<>();
        SpuVO spuVO = null;

        for (Spu spu : spuList) {
            spuVO = new SpuVO();
            BeanUtils.copyProperties(spu, spuVO);
            spuVO.setId(spu.getSpuId());
            // 查询类目的名称
            List<String> categoryNameList = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList());
            spuVO.setCname(StringUtils.join(categoryNameList, "/"));

            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuVO.setBname(brand == null ? "" : brand.getBrandName());

            spuVOList.add(spuVO);
        }

        return spuVOList;
    }
}
