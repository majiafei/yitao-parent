package com.yitao.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtils
 * @Auther: admin
 * @Date: 2019/6/5 14:16
 * @Description:
 */

@Log4j2
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String fromObjectToString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
       return JSONObject.parseArray(json, clazz);
    }

    public static <K,V> Map<K,V> jsonToMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("parse failed", e);
            return null;
        }
    }

    public static <T> T readValue(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("parse failed", e);
            return null;
        }
    }
}
