package com.yitao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: house
 * @Package: com.yitao.common.utils
 * @ClassName: DateUtils
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 15:49
 */
public class DateUtils {

    public static final String dateFormat = "yyyy-MM-dd";
    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public static String dateToStr(Date date, String fomat) {
        try {
            return new SimpleDateFormat(fomat).format(date);
        } catch (Exception e) {
            return null;
        }
    }

}
