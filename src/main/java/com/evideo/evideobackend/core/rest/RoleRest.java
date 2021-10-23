package com.evideo.evideobackend.core.rest;

import com.evideo.evideobackend.adminlte.rest.MovieTypeRest;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.RoleServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/role")
public class RoleRest {
    static Logger log = Logger.getLogger(MovieTypeRest.class.getName());
    private static String key;

    final RoleServiceImplement roleService;
    RoleRest(RoleServiceImplement roleService) {
        this.roleService = roleService;
        key = GenerateRandomPassword.key() + "::";
    }

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObject> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            JsonObjectArray restData = this.roleService.read(jsonObject);

            responseData.setBody(restData);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Read Response Http Client Data :"+objectMapper.writeValueAsString(responseData));
        return responseData;
    }
}
