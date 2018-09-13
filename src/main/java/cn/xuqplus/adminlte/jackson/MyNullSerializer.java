package cn.xuqplus.adminlte.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MyNullSerializer {

    static final JsonSerializer<Object> _nullStringSerializer = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeString("");
            } else {
                gen.writeObject(value);
            }
        }
    };

    static final JsonSerializer<Object> _nullArraySerializer = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeStartArray();
                gen.writeEndArray();
            } else {
                gen.writeObject(value);
            }
        }
    };

    static final JsonSerializer<Object> _nullMapSerializer = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeStartObject();
                gen.writeEndObject();
            } else {
                gen.writeObject(value);
            }
        }
    };

    static final JsonSerializer<Object> _nullNumberSerializer = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNumber(0);
            } else {
                gen.writeObject(value);
            }
        }
    };

    static final JsonSerializer<Object> _nullBooleanSerializer = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeString("");
            } else {
                gen.writeObject(value);
            }
        }
    };

    static final JsonSerializer<Object> _nullObjectSerializer = _nullMapSerializer;
}
