package com.yitao.common.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * @ClassName: RestTemplateUtils
 * @Auther: admin
 * @Date: 2019/6/5 13:42
 * @Description:
 */
@Log4j2
public class RestTemplateUtils {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static <T> T getForEntity(URI uri, Class<T> responseType) {
        try {
            return REST_TEMPLATE.getForEntity(uri, responseType).getBody();
        } catch (Exception e) {
            log.error("get entity failed", e);
            return null;
        }
    }

    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        try {
            return REST_TEMPLATE.getForEntity(url, responseType, uriVariables);
        } catch (Exception e) {
            log.error("get result from url=" + url, e);
            return null;
        }
    }

    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) {
        return REST_TEMPLATE.getForEntity(url, responseType, uriVariables);
    }

}
