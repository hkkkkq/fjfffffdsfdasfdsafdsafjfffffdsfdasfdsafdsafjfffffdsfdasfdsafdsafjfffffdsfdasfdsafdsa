package com.henc.cdrs.common.json.serialize;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class DateJsonDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {
    private String format;

    public DateJsonDeserializer() {}

    public DateJsonDeserializer(String format) {
        this.format = format;
    }

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = parser.getText();
        Date date = null;
        if (StringUtils.isNotEmpty(text)) {
            try {
                date = DateUtils.parseDate(text, format);
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
        return date;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        JsonDateFormat format = property.getMember().getAnnotation(JsonDateFormat.class);
        return new DateJsonDeserializer(format.value());
    }
}