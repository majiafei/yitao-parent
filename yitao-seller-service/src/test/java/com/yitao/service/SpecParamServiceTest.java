package com.yitao.service;

import com.yitao.YitaoSellerServiceApplicationTests;
import com.yitao.domain.SpecParam;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ProjectName: house
 * @Package: com.yitao.service
 * @ClassName: SpecParamService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/10 21:45
 */
public class SpecParamServiceTest extends YitaoSellerServiceApplicationTests {

    @Autowired
    private SpecParamService specParamService;

    @Test
    public void testQuerySpecParamByCondition() {
/*        SpecParam specParam = new SpecParam();
        specParam.setCategoryId(76L);
        specParam.setSpecGroupId(2L);
        List<SpecParam> specParamList = specParamService.querySpecParamListByCondition(specParam);
//        Assert.assertEquals(18, specParamList.size());
        Assert.assertEquals(3L, specParamList.size());*/

        SpecParam specParam = new SpecParam();
        specParam.setCategoryId(76L);
        specParam.setSpecGroupId(100L);
//        List<SpecParam> specParamList = specParamService.querySpecParamListByCondition(specParam);
//        Assert.assertEquals(0L, specParamList.size());
    }

    @Test
    public void testRange() {
        String seg = "0-1.0,1.0-1.5,1.5-2.0,2.0-2.5,2.5-";
        String[] strings = StringUtils.tokenizeToStringArray(seg, ",");
        Set<Double> set = new TreeSet<>();
        for (String string : strings) {
            String[] strings1 = StringUtils.tokenizeToStringArray(string, "-");
            for (String str : strings1) {
                set.add(Double.valueOf(str));
            }
        }

        double d = 10;

        List<Double> doubleList = new ArrayList<>(set);

        double big = 0.0;
        double small = 0.0;

        String result = "";

        if (doubleList.get(doubleList.size() - 1).doubleValue() <= d) {
            small = doubleList.get(doubleList.size() - 1);
            result = small + "以上";
        }

        if (doubleList.get(0).doubleValue() > d) {
            big = doubleList.get(0);
            result = big + "以下";
        }

        if (!StringUtils.hasText(result)) {
            for (int i = 1; i < doubleList.size(); i++) {
                Double aDouble = doubleList.get(i);
                if (d < aDouble.doubleValue()) {
                    if (doubleList.get(i - 1).doubleValue() == 0.0) {
                        result = aDouble + "以下";
                    } else {
                        result = doubleList.get(i - 1).doubleValue() + "~" + aDouble;
                    }
                    break;
                }
            }
        }

        System.out.println(result);

    }


}
