package com.parkey.modernjava.jsontest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KMessageTest {

    @Test
    void updateTest() throws JsonProcessingException {
        // JSON string
        String json = "{\n" +
                "    \"table\": \"policy\",\n" +
                "    \"type\": \"update\",\n" +
                "    \"row\": {\n" +
                "      \"after\": [{\"id\":1},{\"groupKey\":\"s\"}],\n" +
                "      \"before\": [{\"id\":1},{\"groupKey\":\"e\"}]\n" +
                "    }\n" +
                "}";

        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(KMessage.class, new KMessageDeserializer());
        mapper.registerModule(module);

        // convert JSON string to Java Object
        KMessage kMessage = mapper.readValue(json, KMessage.class);

        // print user object
        System.out.println(kMessage);

//        // get properties from address
//        System.out.println(kMessage.getAddress().get("city"));
//        System.out.println(kMessage.getAddress().get("country"));
    }

    @Test
    void insertTest() throws JsonProcessingException {
        // JSON string
        String json = "{\n" +
                "    \"table\": \"policy\",\n" +
                "    \"type\": \"insert\",\n" +
                "    \"row\": [\n" +
                "      {\"id\":1},\n" +
                "      {\"groupkey\":\"s\"}\n" +
                "    ]\n" +
                "}";

        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Java Object
        KMessage kMessage = mapper.readValue(json, KMessage.class);

        // print user object
        System.out.println(kMessage);

        // get properties from address
//        System.out.println(kMessage.getRow().getMap().get("id"));
//        System.out.println(kMessage.getRow().getMap().get("groupKey"));
    }
}