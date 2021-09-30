package com.evideo.evideobackend.mobile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello";
    }
    public String index(@RequestBody JsonNode jsonNode) {

        try {
            String jsonString = "{\"k1\":\"v1\"}";

            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(jsonString);
        }catch (Exception e) {

        }


        return "";
    }
}
