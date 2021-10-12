package com.evideo.evideobackend.adminlte.rest;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/videoSource")
public class VideoSourceRest {
    static Logger log = Logger.getLogger(VideoSourceRest.class.getName());
    private String key;

    final WriteFileServiceImplement writeFileService;
    final VideoSourceLTEServiceImplement videoSourceLTEService;
    VideoSourceRest(WriteFileServiceImplement writeFileService, VideoSourceLTEServiceImplement videoSourceLTEServiceImplement) {
        this.writeFileService = writeFileService;
        this.videoSourceLTEService = videoSourceLTEServiceImplement;
        key = GenerateRandomPassword.generateRandomPassword(5);
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.videoSourceLTEService.read(jsonObject);
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception | ValidatorException e) {
            log.info("Exception :"+e.getMessage());
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
        }
        log.info("Movie Type Rest Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try {
            log.info(key + "Request Data from http Client :" + objectMapper.writeValueAsString(jsonNode));

            int vdId = jsonNode.get("vdId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();
            String videoSourceOnSchedule = jsonNode.get("videoSourceOnSchedule").asText();
            int videoSourcePart = jsonNode.get("videoSourcePart").asInt();
            String isEnd = jsonNode.get("isEnd").asText();
            JsonNode fileInf = jsonNode.get("fileInfo");
            String fileBits = "";
            String fileName = "";
            String fileExtension = "";

            if (vdId <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (vdName == null || vdName.equals("")) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            } else if (videoSourcePart <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVideoSourcePart");
                responseData.setResult(header);
                return responseData;
            } else if (fileInf == null) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidFileImage");
                responseData.setResult(header);
                return responseData;
            } else if (fileInf != null) {
                fileBits = fileInf.get("fileBits").asText();
                fileName = fileInf.get("fileName").asText();
                fileExtension = fileInf.get("fileExtension").asText();

                if (fileBits == null || fileBits.equals("")) {
                    header.setResponseCode(StatusCode.notFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileName == null || fileName.equals("")) {
                    header.setResponseCode(StatusCode.notFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileExtension == null || fileExtension.equals("")) {
                    header.setResponseCode(StatusCode.notFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                }
            }

            String path = "/uploads/video/"+vdName+"/";
            int resourceId = this.writeFileService.writeFile(userId,fileName.replaceAll("\\s+",""), fileExtension, path, fileBits);

            String scheduleYN = "N";
            if (videoSourceOnSchedule !=null || !videoSourceOnSchedule.equals("")) {
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
                responseData.setBody(header);
                log.info(key+"VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
                return responseData;
            }
            header.setResponseCode(StatusCode.notFound);
            header.setResponseMessage(MessageCode.exception);

        }catch (Exception | ValidatorException e) {
            log.info("Exception error :" + String.valueOf(e));
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
        }
        responseData.setResult(header);
        log.info(key+" VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/requestPart")
    public ResponseData<JsonObject> inquiryPart(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
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
            e.printStackTrace();
            log.info("Exception error :" + e.getMessage());
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
        }
        log.info(key+" VideoSourceRest Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }


}
