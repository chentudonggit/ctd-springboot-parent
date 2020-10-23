package com.ctd.springboot.common.core.utils.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * XmlUtils
 *
 * @author chentudong
 * @date 2020/10/23 15:41
 * @since 1.0
 */
public class XmlUtils {

    /**
     * toXmlString
     *
     * @param object object
     * @return String
     * @throws JsonProcessingException JsonProcessingException
     */
    public static String toXmlString(Object object) throws JsonProcessingException {
        if (Objects.isNull(object)) {
            return null;
        }
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JaxbAnnotationModule());
        return mapper.writeValueAsString(object);
    }

    /**
     * replaceAll
     *
     * @param source      source
     * @param replacement replacement
     * @param regex       regex
     * @return String
     */
    public static String replaceAll(String source, String replacement, String... regex) {
        if (StringUtils.isNotBlank(source) && Objects.nonNull(replacement) && Objects.nonNull(regex)) {
            for (String s : regex) {
                source = source.replaceAll(s, replacement);
            }
        }
        return source;
    }
}
