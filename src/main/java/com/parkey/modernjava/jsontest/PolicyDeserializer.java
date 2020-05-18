package com.parkey.modernjava.jsontest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PolicyDeserializer extends JsonDeserializer<Policy> {
    private final ObjectMapper objectMapper;

    PolicyDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Policy deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode jsonNode = objectCodec.readTree(p);

        final long id = jsonNode.get(0).get("id").asLong();
        final String groupKey = jsonNode.get(1).get("groupKey").asText();

        return new Policy(id, groupKey);
    }
}
