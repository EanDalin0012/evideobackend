package com.evideo.evideobackend.unsecur.web.rest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.unsecur.web.service.implement.VideoUnsecurServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/unsecur/web/video/api")
public class WebVideoUnsecurRest {

	 static Logger log = Logger.getLogger(WebVideoUnsecurRest.class.getName());
	 private String key;

	 final VideoUnsecurServiceImplement videoUnsecurService;
	 WebVideoUnsecurRest(VideoUnsecurServiceImplement videoUnsecurService) {
		 key = GenerateRandomPassword.key() + "::";
		 this.videoUnsecurService = videoUnsecurService;
	 }
	 
	 @PostMapping(value = "/v0/read")
    public ResponseData<JsonObjectArray> read(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
//        	Thread.sleep(5000);
        	log.info(key + "JsonNode :"+objectMapper.writeValueAsString(jsonNode));
        	int LIMIT = jsonNode.get("LIMIT").asInt();
        	int OFFSET = jsonNode.get("OFFSET").asInt();
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            jsonObject.setInt("LIMIT", LIMIT);
            jsonObject.setInt("OFFSET", OFFSET);
            
            
            JsonObjectArray restData = this.videoUnsecurService.read(jsonObject);
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            responseData.setResult(header);
        }

        log.info(key+"Movie Type Rest Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }
	 
}
