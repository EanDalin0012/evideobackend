package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.service.implement.MovieDetailsServiceImplement;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
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
@RequestMapping(value = "/api/movie-detail")
public class MovieDetailsRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private String key;
    final MovieDetailsServiceImplement movieDetailsService;
    MovieDetailsRest(MovieDetailsServiceImplement movieDetailsService) {
        this.movieDetailsService = movieDetailsService;
        key = GenerateRandomPassword.key() + "::";
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try {
            log.info(key+"MovieDetailsRest Data from http client : "+objectMapper.writeValueAsString(jsonNode));

            int vdTypeId = jsonNode.get("vdId").asInt();
            int subVdTypeId = jsonNode.get("subVdTypeId").asInt();
            String status = jsonNode.get("status").asText();

            JsonObject inquiry = new JsonObject();
            inquiry.setInt("vdTypeId", vdTypeId);
            inquiry.setInt("subVdTypeId", subVdTypeId);
            JsonObject json = this.movieDetailsService.inquiry(inquiry);

            String localDate = CurrentDateUtil.get();
            if (json == null) {
                // add
                int id = this.movieDetailsService.count();
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("id", id);
                jsonObject.setInt("vdTypeId", vdTypeId);
                jsonObject.setInt("subVdTypeId", subVdTypeId);
                jsonObject.setString("status", status);
                jsonObject.setInt("userId", userId);
                jsonObject.setString("createAt", localDate);
                int save = this.movieDetailsService.create(jsonObject);
                if (save > 0 ) {
                    responseData.setBody(header);
                    responseData.setResult(header);
                    return responseData;
                }
            } else {
                // update
                int upDateId = jsonNode.get("id").asInt();
                JsonObject objUpdate = new JsonObject();
                objUpdate.setInt("id", upDateId);
                objUpdate.setInt("vdTypeId", vdTypeId);
                objUpdate.setInt("subVdTypeId", subVdTypeId);
                objUpdate.setString("status", status);
                objUpdate.setInt("userId", userId);
                objUpdate.setString("modifyAt", localDate);
                int update = this.movieDetailsService.update(objUpdate);
                if (update > 0 ) {
                    responseData.setBody(header);
                    responseData.setResult(header);
                    return responseData;
                }
            }

            header.setResponseCode(StatusCode.notFound);
            header.setResponseMessage(StatusCode.notFound);

        }catch (Exception | ValidatorException e) {
            log.error(key+"MovieDetailsRest Exception :", e);
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            log.info(key+"MovieDetailsRest Data http client data :"+objectMapper.writeValueAsString(jsonNode));
            int vdId = jsonNode.get("vdId").asInt();
            if (vdId > 0) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.setInt("vdId", vdId);
                JsonObjectArray array = this.movieDetailsService.read(jsonObject);
                responseData.setResult(header);
                responseData.setBody(array);
                log.info(key+"MovieDetailsRest response http client data :"+objectMapper.writeValueAsString(responseData));
                return responseData;
            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("inValidVdId");
            }

        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
        }
        responseData.setResult(header);
        return responseData;

    }

    @PostMapping(value = "/v0/inquiry")
    public ResponseData<JsonObject> inquiry(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        return responseData;

    }
}
