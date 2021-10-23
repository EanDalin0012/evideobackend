package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.common.SettingClientStatus;
import com.evideo.evideobackend.adminlte.service.implement.MovieTypeServiceImplement;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie-type")
public class MovieTypeRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private static String key;

    final MovieTypeServiceImplement movieTypeService;
    MovieTypeRest(MovieTypeServiceImplement movieTypeService) {
        key = GenerateRandomPassword.key() + "::";
        this.movieTypeService = movieTypeService;
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            log.info(key+"Movie Type Rest Client Request Data : "+objectMapper.writeValueAsString(jsonNode));

            int id = this.movieTypeService.count();
            String fullName = jsonNode.get("fullName").asText();

            String name = jsonNode.get("name").asText();
            String remark = jsonNode.get("remark").asText();
            String localDate = CurrentDateUtil.get();
            if (name ==null || name == "") {
                header.setResponseMessage("Invalid_Name");
                responseData.setResult(header);
                return responseData;
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setString("vdType", name);
                jsonObject.setString("remark", remark);
                jsonObject.setString("createAt", localDate);
                jsonObject.setString("status", Status.active);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("settingClient", SettingClientStatus.N);
                int save = this.movieTypeService.create(jsonObject);
                if (save > 0) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    log.info(key+"Movie Type Response to Http Client : "+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            responseData.setResult(header);
        }
        log.info(key+"Movie Type Response to Http Client : "+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.movieTypeService.read(jsonObject);
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

    @PostMapping(value = "/v0/delete")
    public ResponseData<JsonObject> delete(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try {
            log.info(key+"Data from http client :"+objectMapper.writeValueAsString(jsonNode));
            int id = jsonNode.get("id").asInt();
            if ( id > 0) {
                String localDate = CurrentDateUtil.get();
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("status", Status.delete);
                jsonObject.setString("modifyAt", localDate);
                int update = this.movieTypeService.delete(jsonObject);
                if (update > 0) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    log.info(key+"Delete Success. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            } else {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("Invalid_Vd_Id");
            }

        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception Error delete :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
        }
        responseData.setResult(header);
        log.info(key+"Delete Fail. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/update")
    public ResponseData<JsonObject> update(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            log.info(key+"Data from http client :"+objectMapper.writeValueAsString(jsonNode));
            int id = jsonNode.get("id").asInt();
            String name = jsonNode.get("name").asText();
            String remark = jsonNode.get("remark").asText();
            if (id > 0) {
                String localDate = CurrentDateUtil.get();
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setString("vdType", name);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("modifyAt", localDate);
                jsonObject.setString("status", Status.modify);
                jsonObject.setString("remark", remark);
                int update = this.movieTypeService.update(jsonObject);
                if (update > 0) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return responseData;
                }
            } else {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("Invalid_Vd_Id");
            }
        } catch (Exception | ValidatorException e) {
            log.error(key+"Exception Error delete :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/updateStatusYN")
    public ResponseData<JsonObject> updateStatusYN(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try {
            log.info(key+"Data from http client :"+objectMapper.writeValueAsString(jsonNode));
            int id = jsonNode.get("id").asInt();
            String status = jsonNode.get("status").asText();
            if (status ==null || status.equals("")) {
                header.setResponseMessage("invalidStatus");
                header.setResponseCode(StatusCode.NotFound);
                responseData.setResult(header);
                return responseData;
            }
            if ( id > 0) {
                String localDate = CurrentDateUtil.get();
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("settingClient", status);
                jsonObject.setString("modifyAt", localDate);
                int update = this.movieTypeService.updateStatusYN(jsonObject);
                if (update > 0) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    log.info(key+"updateStatusYN Success. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
                    return responseData;
                }
            } else {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("Invalid_Vd_Id");
            }

        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception Error delete :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Delete Fail. Data Response to http Client :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }
}
