package com.henc.cdrs.common.json.serialize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
/**
 * @JsonDateFormat("yyyy-MM-dd")
 * @JsonSerialize(using = DateJsonSerializer.class)
 * private Date date;
 *
 * 나중에는 henc-hr에만 위치하면 됨..
 *
 * @author YongSang
 */
public class DateJsonSerializer extends JsonSerializer<Date> implements ContextualSerializer {
    private String format;

    public DateJsonSerializer() {}

    public DateJsonSerializer(String format) {
        this.format = format;
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeString(new SimpleDateFormat(format).format(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        JsonDateFormat format = property.getMember().getAnnotation(JsonDateFormat.class);
        return new DateJsonSerializer(format.value());
    }
}