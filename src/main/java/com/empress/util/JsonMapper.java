package com.empress.util;


import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * JSON转换工具
 *
 * @author Hystar
 * @date 2018/7/5
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 对象转换成String
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String objToString(T t) {
        if (t == null) {
            return null;
        }

        try {
            return t instanceof String ? (String) t : objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            log.error("对象转换成String出现异常，error：{}", e);
            return null;
        }
    }

    /**
     * String转换成对象
     *
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (str == null || typeReference == null) {
            return null;
        }

        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            log.error("String转换成对象出现异常就，String={}，TypeReference<T>={}，error={}", str, typeReference.getType(), e);
            return null;
        }
    }
}
