package com.parkey.modernjava.jsontest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class KMessage {
    private String type;
    private String table;
    private Row row;


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Row {
    @JsonProperty("after")
    private List<FieldInfo> after = new ArrayList<>();
    @JsonProperty("before")
    private List<FieldInfo> before = new ArrayList<>();
}

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Policy.class, name = "policy"),
//        @JsonSubTypes.Type(value = PolicyRole.class, name = "policyRole")
//})
@NoArgsConstructor
abstract class FieldInfo {
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Policy extends FieldInfo {
    private Long id;
    private String groupKey;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PolicyRole extends FieldInfo {
    private Long id;
    private Long policyId;
}