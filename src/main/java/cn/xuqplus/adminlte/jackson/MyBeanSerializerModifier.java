package cn.xuqplus.adminlte.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MyBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(
            SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            Class clazz = writer.getPropertyType();
            if (isStringType(clazz)) {
                writer.assignNullSerializer(MyNullSerializer._nullStringSerializer);
            } else if (isArrayType(clazz)) {
                writer.assignNullSerializer(MyNullSerializer._nullArraySerializer);
            } else if (isMapType(clazz)) {
                writer.assignNullSerializer(MyNullSerializer._nullMapSerializer);
            } else if (isNumberType(clazz)) {
                writer.assignNullSerializer(MyNullSerializer._nullNumberSerializer);
            } else if (isBooleanType(clazz)) {
                writer.assignNullSerializer(MyNullSerializer._nullBooleanSerializer);
            } else {
                writer.assignNullSerializer(MyNullSerializer._nullObjectSerializer);
            }
        }
        return beanProperties;
    }

    protected boolean isStringType(Class clazz) { // String, Character
        return String.class.equals(clazz) || Character.class.equals(clazz);
    }

    protected boolean isArrayType(Class clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    protected boolean isMapType(Class clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    protected boolean isNumberType(Class clazz) {
        return Number.class.isAssignableFrom(clazz);
    }

    private boolean isBooleanType(Class clazz) {
        return Boolean.class.equals(clazz);
    }
}
