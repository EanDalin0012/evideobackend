package com.evideo.evideobackend.unsecur.web.rest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.evideo.evideobackend.unsecur.web.service.implement.SubVideoTypeUnsecurServiceImplement;

@RestController
@RequestMapping(value = "/unsecur/web/videoSubType/api")
public class WebVideoSubTypeUnsecurRest {

	 static Logger log = Logger.getLogger(WebVideoSubTypeUnsecurRest.class.getName());
	 private String key;
	 
	 final SubVideoTypeUnsecurServiceImplement subVideoTypeUnsecurService;
	 WebVideoSubTypeUnsecurRest(SubVideoTypeUnsecurServiceImplement subVideoTypeUnsecurService) {
		 key = GenerateRandomPassword.key() + "::";
		 this.subVideoTypeUnsecurService = subVideoTypeUnsecurService;
	 }
	 
	    @GetMapping(value = "/v0/read")
	    public ResponseData<JsonObjectArray> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
	        Header header = new Header(StatusCode.Success, MessageCode.Success);
	        try {
	            JsonObject jsonObject = new JsonObject();
	            jsonObject.setString("status", Status.delete);
	            JsonObjectArray restData = this.subVideoTypeUnsecurService.read(jsonObject);
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
