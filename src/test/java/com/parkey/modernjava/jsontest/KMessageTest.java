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
    
    @Test
    public void mapTest() throws IOException {
        String json = "{\"schema\":\"yeogi\",\"row\":{\"after\":[{\"id\":16},{\"idType\":1},{\"channelId\":2},{\"agencyId\":1},{\"placeId\":1},{\"spaceId\":1},{\"groupKey\":\"aa\"},{\"targetId\":0},{\"deletedFlags\":0},{\"createdAt\":\"2020-03-03 10:51:10\"},{\"updatedAt\":\"2014-01-08 14:38:52\"}],\"before\":[{\"id\":16},{\"idType\":1},{\"channelId\":2},{\"agencyId\":1},{\"placeId\":1},{\"spaceId\":1},{\"groupKey\":\"bb\"},{\"targetId\":0},{\"deletedFlags\":0},{\"createdAt\":\"2020-03-03 10:51:10\"},{\"updatedAt\":\"2014-01-08 14:38:52\"}]},\"table\":\"policy\",\"type\":\"UpdateRowsEventV2\",\"@version\":\"1\",\"@timestamp\":\"2020-03-03T01:51:10.680Z\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode row = jsonNode.get("row").get("after");

        Optional<Map> collect = StreamSupport.stream(row.spliterator(), true)
                .map(jsonNode1 -> mapper.convertValue(jsonNode1, Map.class))
                .reduce(CompositeMap::new);

        Map map = collect.get();

        System.out.println(map.get("id"));
        System.out.println(map.get("idType"));
        System.out.println(map.get("channelId"));

//         ModelMapper modelMapper = new ModelMapper();
//         Policy Policy = modelMapper.map(map, Policy.class);
//         System.out.println(Policy);

    }
}
