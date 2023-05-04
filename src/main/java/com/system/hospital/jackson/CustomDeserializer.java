package com.system.hospital.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.system.hospital.dto.PatientDto;

import java.io.IOException;

public class CustomDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken != JsonToken.VALUE_STRING) {
            throw new JsonParseException(jsonParser, "Expected a string value");
        }
        return jsonParser.getText().trim();
    }
}
