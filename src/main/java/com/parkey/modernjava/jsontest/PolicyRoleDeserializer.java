package com.parkey.modernjava.jsontest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PolicyRoleDeserializer extends JsonDeserializer<PolicyRole> {
    private final ObjectMapper objectMapper;

    public PolicyRoleDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PolicyRole deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode jsonNode = objectCodec.readTree(p);

        final long id = jsonNode.get("id").asLong();
        final long policyId = jsonNode.get("policyId").asLong();

        return new PolicyRole(id, policyId);
    }
}
