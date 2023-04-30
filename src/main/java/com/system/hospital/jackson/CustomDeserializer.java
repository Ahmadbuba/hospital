package com.system.hospital.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomDeserializer extends StdDeserializer<String> {
    protected CustomDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        int id = currentToken.id();
        if(id != JsonToken.VALUE_STRING.id()) {
            throw new IOException("Field value is not string json type");
        } else {
            return currentToken.asString();
        }
    }
}
