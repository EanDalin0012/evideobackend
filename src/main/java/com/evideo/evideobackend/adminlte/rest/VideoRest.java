package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.event.RemoveFileEvent;
import com.evideo.evideobackend.adminlte.service.implement.VideoServiceImplement;
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
@RequestMapping(value = "/api/video")
public class VideoRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private String key;
    
    @Inject
    private ApplicationEventPublisher eventPublisher;

    final VideoServiceImplement videoService;
    final WriteFileServiceImplement writeFileService;

    VideoRest(VideoServiceImplement videoService, WriteFileServiceImplement writeFileService) {
        this.videoService = videoService;
        this.writeFileService = writeFileService;
        key = GenerateRandomPassword.key() + "::";
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoRest Create ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try{
            log.info(key+"Request Data from http Client :"+objectMapper.writeValueAsString(jsonNode));

            int vdId = jsonNode.get("vdId").asInt();
            int subVdTypeId = jsonNode.get("subVdTypeId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();

            int sourceId = jsonNode.get("sourceId").asInt();
            
            if (vdId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (subVdTypeId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidSubVdTypeId");
                responseData.setResult(header);
                return responseData;
            } else if (vdName == null || vdName.equals("")) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
//                invalidFileImage
            } else if (sourceId <= 0) {
            	 header.setResponseCode(StatusCode.NotFound);
                 header.setResponseMessage("invalidFileImage");
                 responseData.setResult(header);
                 return responseData;
            }
            
            int id = this.videoService.count();
            String localDate = CurrentDateUtil.get();

            JsonObject jsonObject = new JsonObject();
            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setInt("vdId", vdId);
            jsonObject.setInt("subVdTypeId", subVdTypeId);
            jsonObject.setInt("resourceId", sourceId);
            jsonObject.setString("vdName", vdName);
            jsonObject.setString("remark", remark);
            jsonObject.setString("createAt", localDate);
            jsonObject.setString("status", Status.active);

            int save = this.videoService.create(jsonObject);
            if (save > 0) {
                responseData.setResult(header);
                log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
                return responseData;
            }

            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);

        }catch (Exception | ValidatorException e) {
            log.error(key+"VideoRest Exception error :" ,e );
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObjectArray> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        log.info(key+"============= Start VideoRest Read ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.videoService.read(jsonObject);
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception | ValidatorException e) {
            log.error(key+"VideoRest Exception :",e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(e.getMessage());

            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
            responseData.setResult(header);
        }
        log.info("Movie Type Rest Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/update")
    public ResponseData<JsonObject> update(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoRest Update ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try{
//            log.info(key + "VideoRest Request Data From Http Client :"+objectMapper.writeValueAsString(jsonNode));

            int id = jsonNode.get("id").asInt();
            int vdId = jsonNode.get("vdId").asInt();
            int subVdTypeId = jsonNode.get("subVdTypeId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();
            int sourceId = jsonNode.get("sourceId").asInt();
            int oldSourceId = jsonNode.get("oldSourceId").asInt();
            
            
            if (vdId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (subVdTypeId <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidSubVdTypeId");
                responseData.setResult(header);
                return responseData;
            } else if (vdName.equals("")) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            } else if (sourceId <= 0) {
            	 header.setResponseCode(StatusCode.NotFound);
                 header.setResponseMessage("invalidFileImage");
                 responseData.setResult(header);
                 return responseData;
            }

            
            String localDate = CurrentDateUtil.get();
            JsonObject jsonObject = new JsonObject();

            
            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setInt("vdId", vdId);
            jsonObject.setInt("subVdTypeId", subVdTypeId);
            jsonObject.setInt("resourceId", sourceId);
            jsonObject.setString("vdName", vdName);
            jsonObject.setString("remark", remark);
            jsonObject.setString("modifyAt", localDate);
            jsonObject.setString("status", Status.modify);
            log.info(key + "VideoRest jsonObject :"+objectMapper.writeValueAsString(jsonObject));
            int update = this.videoService.update(jsonObject);
            
            if (update > 0) {
                responseData.setResult(header);
                log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
                if (sourceId != oldSourceId) {
                    JsonObject input = new JsonObject();
                    input.setInt("id", oldSourceId);
                    this.eventPublisher.publishEvent(new RemoveFileEvent(input));
                }
                return responseData;
            }

            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);

        }catch (Exception | ValidatorException e) {
            log.error(key+"VideoRest Exception error :" ,e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/delete")
    public ResponseData<JsonObject> delete(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        log.info(key+"============= Start VideoRest Delete ============");

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
                int update = this.videoService.delete(jsonObject);
                if (update > 0) {
                    responseData.setResult(header);
                    log.info(key+"Delete Success. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.NotFound);
        }catch (Exception | ValidatorException e) {
            log.error(key+"VideoRest Exception error :" , e);
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
