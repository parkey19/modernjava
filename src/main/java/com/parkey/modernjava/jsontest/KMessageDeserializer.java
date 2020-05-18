package com.parkey.modernjava.jsontest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class KMessageDeserializer extends JsonDeserializer<KMessage> {

    private final ObjectMapper objectMapper;

    public KMessageDeserializer() {
        this.objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Policy.class, new PolicyDeserializer());
        simpleModule.addDeserializer(PolicyRole.class, new PolicyRoleDeserializer());
        this.objectMapper.registerModule(simpleModule);
    }

    @Override
    public KMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode jsonNode = objectCodec.readTree(p);

        final String table = jsonNode.get("table").asText();
        System.out.println(table);
        final String type = jsonNode.get("type").asText();
        Row row = new Row();
        if ("policy".equalsIgnoreCase(table) && "insert".equalsIgnoreCase(type)) {
            System.out.println("asdfasdkfjaklsdjflkjl");
            final Policy aPolicy = objectMapper.convertValue(jsonNode.get("row"), Policy.class);

        } else if("policy".equalsIgnoreCase(table) && "update".equalsIgnoreCase(type)) {
            System.out.println("@@@@@@@@@@@");
            final Policy aPolicy = objectMapper.convertValue(jsonNode.get("row").get("after"), Policy.class);
            final Policy bPolicy = objectMapper.convertValue(jsonNode.get("row").get("before"), Policy.class);

            row.getAfter().add(aPolicy);
            row.getBefore().add(bPolicy);
            return new KMessage(table, type, row);
        }

//        final CellPhone cellPhone = objectMapper.convertValue(jsonNode.get("cellPhone"), CellPhone.class);
//        final Set<FamilyMember> children = objectMapper.convertValue(jsonNode.get("children"), new TypeReference<LinkedHashSet<FamilyMember>>() {
//        });

        return new KMessage(table, type, null);
    }

}
