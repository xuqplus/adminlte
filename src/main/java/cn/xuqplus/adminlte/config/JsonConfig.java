package cn.xuqplus.adminlte.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.ImmutableList;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JsonConfig implements WebMvcConfigurer {

    //    @Bean
//    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 值为null的字段处理
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 允许单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        // 字段和值都加引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);

        // null处理
        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
//        objectMapper.writer();
//        serializerProvider.getDefaultNullValueSerializer();
//        serializerProvider.setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//            }
//
//        });
        return objectMapper;
    }

    //    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        // 为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为：当序列化类型为array，list、set时，当值为空时，序列化成[]
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));

        converter.setSupportedMediaTypes(ImmutableList.of(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
        return objectMapper;
    }
}
