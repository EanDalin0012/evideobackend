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
import com.evideo.evideobackend.unsecur.web.service.implement.VideoResourceUnsecurServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/unsecur/api/resource")
public class WebVideoResourceUnsecurRest {

	static Logger log = Logger.getLogger(WebVideoResourceUnsecurRest.class.getName());
	private String key;
	
	 final VideoResourceUnsecurServiceImplement videoResourceUnsecurService;
	 WebVideoResourceUnsecurRest(VideoResourceUnsecurServiceImplement videoResourceUnsecurService) {
		 this.videoResourceUnsecurService = videoResourceUnsecurService;
		 key = GenerateRandomPassword.key() + "::";
	 }
	 
	 @PostMapping(value = "/v0/read")
	 public ResponseData<JsonObjectArray> read(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
	        Header header = new Header(StatusCode.Success, MessageCode.Success);
	        try {
//	        	Thread.sleep(5000);
	        	log.info(key + "JsonNode :"+objectMapper.writeValueAsString(jsonNode));
	        	int vdId = jsonNode.get("vdId").asInt();
	        	
	            JsonObject jsonObject = new JsonObject();
	            jsonObject.setString("status", Status.delete);
	            jsonObject.setInt("vdId", vdId);
	            log.info(key +"jsonObject" + objectMapper.writeValueAsString(jsonObject));
	            
	            JsonObjectArray restData = this.videoResourceUnsecurService.read(jsonObject);
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
