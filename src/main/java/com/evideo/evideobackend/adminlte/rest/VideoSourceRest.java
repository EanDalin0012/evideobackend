package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.event.RemoveFileEvent;
import com.evideo.evideobackend.adminlte.service.implement.VideoSourceLTEServiceImplement;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.WriteFileServiceImplement;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/videoSource")
public class VideoSourceRest {
    static Logger log = Logger.getLogger(VideoSourceRest.class.getName());
    private String key;

    @Inject
    private ApplicationEventPublisher eventPublisher;

    final WriteFileServiceImplement writeFileService;
    final VideoSourceLTEServiceImplement videoSourceLTEService;
    VideoSourceRest(WriteFileServiceImplement writeFileService, VideoSourceLTEServiceImplement videoSourceLTEServiceImplement) {
        this.writeFileService = writeFileService;
        this.videoSourceLTEService = videoSourceLTEServiceImplement;
        key = GenerateRandomPassword.key();
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObjectArray> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Read ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
        	
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.videoSourceLTEService.read(jsonObject);
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

    @PostMapping(value = "/v0/inquiry")
    public ResponseData<JsonObjectArray> inquiry(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Inquiry ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            int vdId = jsonNode.get("vdId").asInt();

            if (vdId <=0 ) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return  responseData;
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            jsonObject.setInt("vdId", vdId);
            JsonObjectArray restData = this.videoSourceLTEService.inquiryByVdId(jsonObject);
            responseData.setBody(restData);
            
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Movie Type Rest Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

	@PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Create ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try {
//            log.info(key + "Request Data from http Client :" + objectMapper.writeValueAsString(jsonNode));

            int vdId = jsonNode.get("vdId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();
            String videoSourceOnSchedule = jsonNode.get("videoSourceOnSchedule").asText();
            int videoSourcePart = jsonNode.get("videoSourcePart").asInt();
            String isEnd = jsonNode.get("isEnd").asText();
            int resourceId = jsonNode.get("videoSourceId").asInt();
            
            if (vdId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (vdName == null || vdName.equals("")) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            } else if (videoSourcePart <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVideoSourcePart");
                responseData.setResult(header);
                return responseData;
            }
            
            String scheduleYN = "N";
            if (videoSourceOnSchedule != null) {
                scheduleYN = "Y";
            }

            JsonObject jsonObject = new JsonObject();
            int nextval = this.videoSourceLTEService.count();
            String localDate = CurrentDateUtil.get();
            jsonObject.setInt("id", nextval);
            jsonObject.setInt("userId", userId);
            jsonObject.setString("createAt", localDate);
            jsonObject.setString("status", Status.active);
            jsonObject.setInt("vdId", vdId);
            jsonObject.setInt("part", videoSourcePart);
            jsonObject.setString("scheduleYN", scheduleYN);
            jsonObject.setString("isEnd", isEnd);
            jsonObject.setString("remark", remark);
            jsonObject.setInt("resourceId", resourceId);
            jsonObject.setString("videoSourceOnSchedule", videoSourceOnSchedule);

            int save = this.videoSourceLTEService.create(jsonObject);
            if (save > 0 ) {
                responseData.setResult(header);
                log.info(key+"VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
                return responseData;
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);

        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception error :" , e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+" VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }



    @PostMapping(value = "/v0/requestPart")
    public ResponseData<JsonObject> inquiryPart(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Inquiry Part ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        
        try{
            
        	int vdId = jsonNode.get("vdId").asInt();
            JsonObject jsonObject = new JsonObject();
            jsonObject.setInt("vdId", vdId);
            int part = this.videoSourceLTEService.inquiryPart(jsonObject);
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.setInt("part", part);
            responseData.setResult(header);
            responseData.setBody(jsonResponse);
            log.info(key+"VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
            return responseData;
            
        }catch (Exception | ValidatorException e) {
            log.error("Exception error :" , e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        log.info(key+" VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/update")
    public ResponseData<JsonObject> update(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Update ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try {

            int id          = jsonNode.get("id").asInt();
            int vdId        = jsonNode.get("vdId").asInt();
            String vdName   = jsonNode.get("vdName").asText();
            String remark   = jsonNode.get("remark").asText();
            String videoSourceOnSchedule = jsonNode.get("videoSourceOnSchedule").asText();
            int videoSourcePart     = jsonNode.get("videoSourcePart").asInt();
            String isEnd            = jsonNode.get("isEnd").asText();
            int resourceId          = jsonNode.get("videoSourceId").asInt();
            int oldVideoSourceId	= jsonNode.get("oldVideoSourceId").asInt();

            if (vdId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (vdName.equals("")) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            } else if (videoSourcePart <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVideoSourcePart");
                responseData.setResult(header);
                return responseData;
            }

            String scheduleYN = "Y";
            if (videoSourceOnSchedule.equals("")) {
                scheduleYN = "N";
            }

            JsonObject jsonObject = new JsonObject();
            String localDate = CurrentDateUtil.get();
            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setString("modifyAt", localDate);
            jsonObject.setString("status", Status.modify);
            jsonObject.setInt("vdId", vdId);
            jsonObject.setInt("part", videoSourcePart);
            jsonObject.setString("scheduleYN", scheduleYN);
            jsonObject.setString("isEnd", isEnd);
            jsonObject.setString("remark", remark);
            jsonObject.setInt("resourceId", resourceId);
            jsonObject.setString("videoSourceOnSchedule", videoSourceOnSchedule);

            int update = this.videoSourceLTEService.update(jsonObject);
            if (update > 0 ) {
                responseData.setResult(header);
                
                if (resourceId != oldVideoSourceId) {
                    JsonObject input = new JsonObject();
                    input.setInt("id", oldVideoSourceId);
                    this.eventPublisher.publishEvent(new RemoveFileEvent(input));
                }
                
                log.info(key+"VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
                
                return responseData;
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);

        }catch (Exception | ValidatorException e) {
            log.error("Exception error :" , e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+" VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/delete")
    public ResponseData<JsonObject> delete(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoSourceRest Delete ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            log.info(key+"VideoRest delete Data from http client :"+objectMapper.writeValueAsString(jsonNode));
            int id = jsonNode.get("id").asInt();
            if ( id > 0) {
                String localDate = CurrentDateUtil.get();
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("status", Status.delete);
                jsonObject.setString("modifyAt", localDate);
                int update = this.videoSourceLTEService.delete(jsonObject);
                if (update > 0) {
                    responseData.setResult(header);
                    log.info(key+"Delete Success. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception error :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }


}
