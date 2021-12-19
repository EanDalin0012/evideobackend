package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.dao.ClientSettingDAO;
import com.evideo.evideobackend.adminlte.service.impl.ClientSettingServiceImpl;
import com.evideo.evideobackend.adminlte.service.impl.MovieTypeServiceImpl;
import com.evideo.evideobackend.adminlte.service.impl.SubMovieTypeServiceImpl;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/client-setting")
public class ClientSettingRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private static String key;
    final ClientSettingServiceImpl clientSettingServiceImpl;
    final SubMovieTypeServiceImpl subMovieTypeService;
    final MovieTypeServiceImpl movieTypeService;
    ClientSettingRest( ClientSettingServiceImpl clientSettingServiceImpl, SubMovieTypeServiceImpl subMovieTypeService, MovieTypeServiceImpl movieTypeService) {
        key = GenerateRandomPassword.key() + "::";
        this.clientSettingServiceImpl = clientSettingServiceImpl;
        this.subMovieTypeService = subMovieTypeService;
        this.movieTypeService = movieTypeService;
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.setString("status", Status.delete);
                JsonObjectArray videoSubTypes = this.subMovieTypeService.read(jsonObject);
                JsonObjectArray videoTypes = this.movieTypeService.read(jsonObject);

                JsonObjectArray lst = this.clientSettingServiceImpl.read();

                JsonObject rest = new JsonObject();
                rest.setJsonObjectArray("video_sub_types", videoSubTypes);
                rest.setJsonObjectArray("video_type_dts", lst);
                rest.setJsonObjectArray("video_types", videoTypes);

                responseData.setBody(rest);
                responseData.setResult(header);
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    @PostMapping(value = "/v0/updateVideoTypeDt")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            log.info(key + "jsonNode: "+objectMapper.writeValueAsString(jsonNode));
            boolean checked = jsonNode.get("checked").asBoolean();
            int videoTypeId = jsonNode.get("videoTypeId").asInt();
            int videoSubTypeId = jsonNode.get("videoSubTypeId").asInt();

            JsonObject param = new JsonObject();
            param.setInt("id", this.clientSettingServiceImpl.count());
            param.setInt("videoTypeId", videoTypeId);
            param.setInt("videoSubTypeId", videoSubTypeId);
            if (checked == true) {
                int save = this.clientSettingServiceImpl.insertVideoTypeDt(param);
                if (save > 0) {
                    responseData.setResult(header);
                    return responseData;
                }
            } else {
                int delete = this.clientSettingServiceImpl.deleteVideoTypeDt(param);
                if (delete > 0) {
                    responseData.setResult(header);
                    return responseData;
                }
            }

        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
