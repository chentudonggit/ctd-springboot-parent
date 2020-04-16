package com.ctd.framework.web.converter.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 使用官方自带的json格式类库，fastjson因为content type问题时不时控制台报错、无法直接返回二进制等问题
 * SpringBoot Jackson 将null转字符串"" ，List、Array转[]，Int转0
 *
 * @author chentudong
 * @date 2020/3/29 12:41
 *  { @link https://blog.csdn.net/qq_38132283/article/details/89339817}
 * @since 1.0
 */
public class JacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter
{
    /**
     * 处理数组类型的null值
     */
    public static class NullArrayJsonSerializer extends JsonSerializer<Object>
    {

        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
        {
            if (Objects.isNull(value))
            {
                jgen.writeStartArray();
                jgen.writeEndArray();
            }
        }
    }


    /**
     * 处理字符串类型的null值
     */
    public static class NullStringJsonSerializer extends JsonSerializer<Object>
    {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeString(StringUtils.EMPTY);
        }
    }

    /**
     * 处理数字类型的null值
     */
    public static class NullNumberJsonSerializer extends JsonSerializer<Object>
    {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeNumber(0);
        }
    }

    /**
     * 处理布尔类型的null值
     */
    public static class NullBooleanJsonSerializer extends JsonSerializer<Object>
    {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeBoolean(false);
        }
    }


    public static class MyBeanSerializerModifier extends BeanSerializerModifier
    {
        /**
         * changeProperties
         *
         * @param config         config
         * @param beanDesc       beanDesc
         * @param beanProperties beanProperties
         * @return List<BeanPropertyWriter>
         */
        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties)
        {
            //循环所有的beanPropertyWriter
            for (Object beanProperty : beanProperties)
            {
                BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
                //判断字段的类型，如果是array，list，set则注册nullSerializer
                if (isArrayType(writer))
                {
                    //给writer注册一个自己的nullSerializer
                    writer.assignNullSerializer(new NullArrayJsonSerializer());
                } else if (isNumberType(writer))
                {
                    writer.assignNullSerializer(new NullNumberJsonSerializer());
                } else if (isBooleanType(writer))
                {
                    writer.assignNullSerializer(new NullBooleanJsonSerializer());
                } else if (isStringType(writer))
                {
                    writer.assignNullSerializer(new NullStringJsonSerializer());
                }
            }
            return beanProperties;
        }

        /**
         * isArrayType
         *
         * @param writer writer
         * @return boolean
         */
        private boolean isArrayType(BeanPropertyWriter writer)
        {
            Class<?> clazz = writer.getType().getRawClass();
            return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
        }

        /**
         * isStringType
         *
         * @param writer writer
         * @return boolean
         */
        private boolean isStringType(BeanPropertyWriter writer)
        {
            Class<?> clazz = writer.getType().getRawClass();
            return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
        }


        /**
         * isNumberType
         *
         * @param writer writer
         * @return boolean
         */
        private boolean isNumberType(BeanPropertyWriter writer)
        {
            return Number.class.isAssignableFrom(writer.getType().getRawClass());
        }

        /**
         * isBooleanType
         *
         * @param writer writer
         * @return boolean
         */
        private boolean isBooleanType(BeanPropertyWriter writer)
        {
            Class<?> clazz = writer.getType().getRawClass();
            return clazz.equals(Boolean.class);
        }

    }

    public JacksonHttpMessageConverter()
    {
        getObjectMapper().setSerializerFactory(getObjectMapper().getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
    }
}
