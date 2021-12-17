package com.evideo.evideobackend.adminlte.rest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.evideo.evideobackend.adminlte.service.implement.VdTypeDetailServiceImplement;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/vdTypeDt")
public class VdTypeDetailRest {
	 static Logger log = Logger.getLogger(VdTypeDetailRest.class.getName());
	 private static String key;
	 
	 final VdTypeDetailServiceImplement vdTypeDetailService;
	 VdTypeDetailRest(VdTypeDetailServiceImplement detailService) {
		 this.vdTypeDetailService = detailService;
		 key = GenerateRandomPassword.key() + "::";
	 }
	 
	 @GetMapping(value = "/v0/read")
	 public ResponseData<JsonObjectArray> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            JsonObjectArray restData = this.vdTypeDetailService.read();
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            responseData.setResult(header);
            
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }

        log.info(key+"VdTypeDetailRest Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        
        return responseData;
	 }
	 
	 @PostMapping(value = "/v0/read/vdSubType")
	 public ResponseData<JsonObjectArray> readVdSubType(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
        	int vdTypeId = jsonNode.get("vdTypeId").asInt();
        	JsonObject queryInput = new JsonObject();
        	queryInput.setInt("vdTypeId", vdTypeId);
            JsonObjectArray restData = this.vdTypeDetailService.readVdSubType(queryInput);
            
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception | ValidatorException e ) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            responseData.setResult(header);
            
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }

        log.info(key+"VdTypeDetailRest Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        
        return responseData;
	 }
	 
	@PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        
        try {
        	log.info(key+"VdTypeDetailRest Rest Client Request Data : "+objectMapper.writeValueAsString(jsonNode));
        	
        	int id = this.vdTypeDetailService.count();
        	int vdTypeId = jsonNode.get("vdTypeId").asInt();
        	int vdSubTypeId = jsonNode.get("vdSubTypeId").asInt();
        	
        	if(vdTypeId <=0) {
        		header.setResponseMessage("invalidVdTypeId");
                responseData.setResult(header);
                return responseData;
        	} else if (vdSubTypeId <=0) {
        		header.setResponseMessage("invalidVdSubTypeId");
                responseData.setResult(header);
                return responseData;
        	}
        	
        	JsonObject queryInput = new JsonObject();
        	JsonObject obj = this.vdTypeDetailService.readObj(queryInput);
        	
        	if(obj != null) {
        		header.setResponseMessage("vdSubTypeIdReady");
                responseData.setResult(header);
                return responseData;
        	}
        	
        	JsonObject jsonObject = new JsonObject();
        	jsonObject.setInt("id", id);
        	jsonObject.setInt("vdTypeId", vdTypeId);
        	jsonObject.setInt("vdSubTypeId", vdSubTypeId);
        	
        	int save = this.vdTypeDetailService.create(jsonObject);
        	if(save > 0) {
        		responseData.setResult(header);
                log.info(key+"Movie Type Response to Http Client : "+objectMapper.writeValueAsString(responseData));
                return responseData;
        	}
        	
        	 header.setResponseCode(StatusCode.NotFound);
             header.setResponseMessage(MessageCode.Exception);
        	
        }catch (Exception | ValidatorException e ) {
        	log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }    
		}
        
        responseData.setResult(header);
        return responseData;
	 }
	    
}
