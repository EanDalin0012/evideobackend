package com.evideo.evideobackend.adminlte.rest;

import com.evideo.evideobackend.adminlte.dao.ClientSettingDAO;
import com.evideo.evideobackend.adminlte.service.implement.MovieTypeServiceImplement;
import com.evideo.evideobackend.adminlte.service.implement.SubMovieTypeServiceImplement;
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
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/client-setting")
public class ClientSettingRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private static String key;
    final ClientSettingDAO clientSettingDAO;
    final SubMovieTypeServiceImplement subMovieTypeService;
    final MovieTypeServiceImplement movieTypeService;
    ClientSettingRest(ClientSettingDAO clientSettingDAO, SubMovieTypeServiceImplement subMovieTypeService, MovieTypeServiceImplement movieTypeService) {
        key = GenerateRandomPassword.key() + "::";
        this.clientSettingDAO = clientSettingDAO;
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

                JsonObjectArray lst = this.clientSettingDAO.read();



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

}
