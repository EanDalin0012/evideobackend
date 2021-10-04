package com.evideo.evideobackend.core.rest;

import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    static Logger log = Logger.getLogger(UserRest.class.getName());
    final UserService userService;

    UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/v0/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonObject));
            String userName = jsonObject.getString("userName");
            if(!userName.trim().equals("") || userName != null) {
                JsonObject user = new JsonObject();
                user.setString("userName", userName);
                JsonObject userData = this.userService.loadUserByName(user);
                log.info("User Info :"+objectMapper.writeValueAsString(userData));
                responseData.setBody(userData);
                responseData.setResult(header);
                return responseData;
            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_UserName");
            }
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        responseData.setResult(header);
        return responseData;
    }

}
