package com.evideo.evideobackend.adminlte.rest;

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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/video")
public class VideoRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());

    private String key;
    final VideoServiceImplement videoService;
    final WriteFileServiceImplement writeFileService;

    VideoRest(VideoServiceImplement videoService, WriteFileServiceImplement writeFileService) {
        this.videoService = videoService;
        this.writeFileService = writeFileService;
        key = GenerateRandomPassword.generateRandomPassword(25);
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try{
            log.info(key+"Request Data from http Client :"+objectMapper.writeValueAsString(jsonNode));

            int vdId = jsonNode.get("vdId").asInt();
            int subVdTypeId = jsonNode.get("subVdTypeId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();

            JsonNode fileInf = jsonNode.get("fileInfo");
            String fileBits = fileInf.get("fileBits").asText();
            String fileName = fileInf.get("fileName").asText();
            String fileExtension = fileInf.get("fileExtension").asText();

            if (vdId <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (subVdTypeId <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidSubVdTypeId");
                responseData.setResult(header);
                return responseData;
            } else if (vdName == null || vdName.equals("")) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            } else if (fileInf == null) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidFileImage");
                responseData.setResult(header);
                return responseData;
            } else if (fileInf != null) {
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
            String path = "/uploads/images/";
            int sourceId = this.writeFileService.writeFile(userId,fileName.replaceAll("\\s+",""), fileExtension, path, fileBits);

            if (sourceId > 0 ) {
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
                    responseData.setBody(header);
                    log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            }

            header.setResponseCode(StatusCode.notFound);
            header.setResponseMessage(MessageCode.exception);

        }catch (Exception | ValidatorException e) {
            log.info("Exception error :" + String.valueOf(e));
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);

        }
        responseData.setResult(header);
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.videoService.read(jsonObject);
            responseData.setResult(header);
            responseData.setBody(restData);
        }catch (Exception | ValidatorException e) {
            log.info("Exception :"+String.valueOf(e));
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
        }
        log.info("Movie Type Rest Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/update")
    public ResponseData<JsonObject> update(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try{
            log.info(key + "VideoRest Request Data From Http Client :"+objectMapper.writeValueAsString(jsonNode));

            int id = jsonNode.get("id").asInt();
            int vdId = jsonNode.get("vdId").asInt();
            int subVdTypeId = jsonNode.get("subVdTypeId").asInt();
            String vdName = jsonNode.get("vdName").asText();
            String remark = jsonNode.get("remark").asText();

            if (vdId <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdId");
                responseData.setResult(header);
                return responseData;
            }  else if (subVdTypeId <= 0) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidSubVdTypeId");
                responseData.setResult(header);
                return responseData;
            } else if (vdName == null || vdName.equals("")) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("invalidVdName");
                responseData.setResult(header);
                return responseData;
            }

            int sourceId = 0;
            String localDate = CurrentDateUtil.get();
            JsonObject jsonObject = new JsonObject();
            boolean isSelectedFile = jsonNode.get("isSelectedFile").asBoolean();

            if (isSelectedFile) {
                JsonNode fileInf = jsonNode.get("fileInfo");
                String fileBits = fileInf.get("fileBits").asText();
                String fileName = fileInf.get("fileName").asText();
                String fileExtension = fileInf.get("fileExtension").asText();

                if (fileInf == null) {
                    header.setResponseCode(StatusCode.notFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileInf != null) {
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
                String path = "/uploads/images/";
                sourceId = this.writeFileService.writeFile(userId, fileName.replaceAll("\\s+",""), fileExtension, path, fileBits);
            } else {
                sourceId = jsonNode.get("sourceId").asInt();
            }

            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setInt("vdId", vdId);
            jsonObject.setInt("subVdTypeId", subVdTypeId);
            jsonObject.setInt("resourceId", sourceId);
            jsonObject.setString("vdName", vdName);
            jsonObject.setString("remark", remark);
            jsonObject.setString("modifyAt", localDate);
            jsonObject.setString("status", Status.modify);

            int update = this.videoService.update(jsonObject);
            if (update > 0) {
                responseData.setResult(header);
                responseData.setBody(header);
                log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
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
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/delete")
    public ResponseData<JsonObject> delete(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
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
                    responseData.setBody(header);
                    log.info(key+"Delete Success. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            }
            header.setResponseCode(StatusCode.notFound);
            header.setResponseMessage(MessageCode.exception);
        }catch (Exception | ValidatorException e) {
            log.info("Exception error :" + String.valueOf(e));
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
        }
        log.info(key+"Response data to http client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

}
