package com.evideo.evideobackend.core.rest;

import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dao.AuthorizationDao;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.AuthorizationServiceImplement;
import com.evideo.evideobackend.core.service.implement.UserService;
import com.evideo.evideobackend.core.service.implement.WriteFileServiceImplement;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    static Logger log = Logger.getLogger(UserRest.class.getName());
    private static String key;

    final PlatformTransactionManager transactionManager;
    final UserService userService;
    final AuthorizationDao authorizationDao;
    final WriteFileServiceImplement writeFileService;
    final AuthorizationServiceImplement authorizationService;
    UserRest(UserService userService, PlatformTransactionManager transactionManager, AuthorizationDao authorizationDao, WriteFileServiceImplement writeFileService, AuthorizationServiceImplement authorizationService) {
        this.userService = userService;
        this.transactionManager = transactionManager;
        this.authorizationDao = authorizationDao;
        this.writeFileService = writeFileService;
        this.authorizationService = authorizationService;
        key = GenerateRandomPassword.key() + "::";
    }

    @PostMapping(value = "/v0/checkUserName")
    public ResponseData<JsonObject> checkUserName(@RequestBody JsonNode jsonNode, @RequestParam("lang") String lang, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            String userName = jsonNode.get("userName").asText();
            if (checkUserName(userName) == true) {
                header.setResponseCode(StatusCode.Found);
                header.setResponseMessage(StatusCode.Found);
                responseData.setResult(header);
                responseData.setResult(header);
                responseData.setBody(header);
                return responseData;
            }
            header.setResponseMessage("validUserName");
            responseData.setResult(header);
        }catch (Exception e) {
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            log.error(key+"Exception :", e);
        }

        responseData.setBody(header);
        return responseData;
    }


    @PostMapping(value = "/v0/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonObject));
            String userName = jsonObject.getString("userName");
            if(!userName.trim().equals("") || userName != null) {
                JsonObject user = new JsonObject();
                user.setString("userName", userName);
                JsonObject userData = this.userService.authorizationUser(user);
                log.info("User Info :"+objectMapper.writeValueAsString(userData));
                responseData.setBody(userData);
                responseData.setResult(header);
                return responseData;
            }

            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage("invalidUserName");

        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception ", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            responseData.setResult(header);
            throw e;
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            String fullName = jsonNode.get("fullName").asText();
            String gender   = jsonNode.get("gender").asText();
            String dateBirth = jsonNode.get("dateBirth").asText();
            String phoneNumber = jsonNode.get("phoneNumber").asText();

            String userName = jsonNode.get("userName").asText();
            String password = jsonNode.get("password").asText();
            String remark   = jsonNode.get("remark").asText();
            int roleId      = jsonNode.get("roleId").asInt();
            int id          = this.userService.count();

            JsonNode fileInf = jsonNode.get("fileInfo");
            String fileBits = "";
            String fileName = "";
            String fileExtension = "";

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordEncoder  = bCryptPasswordEncoder.encode(password);

            int resourceId = 0;
            if (checkUserName(userName) == true) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("userReadyHave");
                responseData.setResult(header);
                return responseData;
            } else if (fileInf != null) {
                fileBits = fileInf.get("fileBits").asText();
                fileName = fileInf.get("fileName").asText();
                fileExtension = fileInf.get("fileExtension").asText();

                if (fileBits == null || fileBits.equals("")) {
                    header.setResponseCode(StatusCode.NotFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileName == null || fileName.equals("")) {
                    header.setResponseCode(StatusCode.NotFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileExtension == null || fileExtension.equals("")) {
                    header.setResponseCode(StatusCode.NotFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                }
                String path = "/uploads/profile-upload/";
                resourceId = this.writeFileService.writeFile(userId, fileName.replaceAll("\\s+",""), fileExtension, path, fileBits);
            }



            String localDate = CurrentDateUtil.get();
            JsonObject user = new JsonObject();
            user.setInt("id", id);
            user.setInt("roleId", roleId);
            user.setString("fullName", fullName);

            user.setString("dateBirth", dateBirth);
            user.setString("phoneNumber", phoneNumber);

            user.setString("remark", remark);
            user.setInt("resourceId", resourceId);
            user.setString("status", Status.active);
            user.setString("createAt", localDate);
            user.setString("gender",gender);

            user.setString("userName", userName);
            user.setString("password", passwordEncoder);
            user.setBoolean("enabled", true);
            user.setBoolean("accountExpired", false);
            user.setBoolean("accountLocked", false);
            user.setBoolean("credentialsExpired", false);
            user.setBoolean("isFirstLogin", true);
            log.info(key + "User Info :"+objectMapper.writeValueAsString(user));
            int save = this.userService.addNewUser(user);
            ArrayNode arrayNode  = (ArrayNode) jsonNode.get("authorizationModule");
            int authorityInserted = 0;
            if(arrayNode.isArray()) {
                for(JsonNode json : arrayNode) {
                    JsonObject authority = new JsonObject();
                    authority.setInt("userId", user.getInt("id"));
                    authority.setInt("authorityId", json.get("id").asInt());
                    log.info(key + "Authority Info :"+objectMapper.writeValueAsString(authority));
                    int saveAuth = this.authorizationService.addAuthorizationAccess(authority);
                    if (saveAuth > 0) {
                        authorityInserted += saveAuth;
                    }
                }
            }

            if (save > 0 && authorityInserted > 0) {
                transactionManager.commit(transactionStatus);
                responseData.setResult(header);
                responseData.setBody(header);
                return responseData;
            }

            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.Exception);
            transactionManager.rollback(transactionStatus);

        } catch (Exception | ValidatorException e) {
            log.error(key+"Exception", e);
            transactionManager.rollback(transactionStatus);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
        }
        responseData.setResult(header);
        log.info(key+"Response to Http Client : "+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    private JsonObject setAuthorization(JsonNode jsonNode) {
        JsonObject authorization = new JsonObject();
//            1
        if (jsonNode.get("userRead").asInt() > 0) {
            authorization.setInt("userRead", jsonNode.get("userRead").asInt());
        }
        if (jsonNode.get("userCreate").asInt() > 0) {
            authorization.setInt("userCreate", jsonNode.get("userCreate").asInt());
        }
        if (jsonNode.get("userEdit").asInt() > 0) {
            authorization.setInt("userEdit", jsonNode.get("userEdit").asInt());
        }
        if (jsonNode.get("userDelete").asInt() > 0) {
            authorization.setInt("userDelete", jsonNode.get("userEdit").asInt());
        }
//            2
        if (jsonNode.get("movieRead").asInt() > 0) {
            authorization.setInt("movieRead", jsonNode.get("movieRead").asInt());
        }
        if (jsonNode.get("movieCreate").asInt() > 0) {
            authorization.setInt("movieCreate", jsonNode.get("movieCreate").asInt());
        }
        if (jsonNode.get("movieEdit").asInt() > 0) {
            authorization.setInt("movieEdit", jsonNode.get("movieEdit").asInt());
        }
        if (jsonNode.get("movieDelete").asInt() > 0) {
            authorization.setInt("movieDelete", jsonNode.get("movieDelete").asInt());
        }
//            3
        if (jsonNode.get("movieSourceRead").asInt() > 0) {
            authorization.setInt("movieSourceRead", jsonNode.get("movieSourceRead").asInt());
        }
        if (jsonNode.get("movieSourceCreate").asInt() > 0) {
            authorization.setInt("movieSourceCreate", jsonNode.get("movieSourceCreate").asInt());
        }
        if (jsonNode.get("movieSourceEdit").asInt() > 0) {
            authorization.setInt("movieSourceEdit", jsonNode.get("movieSourceEdit").asInt());
        }
        if (jsonNode.get("movieSourceDelete").asInt() > 0) {
            authorization.setInt("movieSourceDelete", jsonNode.get("movieSourceDelete").asInt());
        }
//            4
        if (jsonNode.get("settingMovieTypeRead").asInt() > 0) {
            authorization.setInt("settingMovieTypeRead", jsonNode.get("settingMovieTypeRead").asInt());
        }
        if (jsonNode.get("settingMovieTypeCreate").asInt() > 0) {
            authorization.setInt("settingMovieTypeCreate", jsonNode.get("settingMovieTypeCreate").asInt());
        }
        if (jsonNode.get("settingMovieTypeEdit").asInt() > 0) {
            authorization.setInt("settingMovieTypeEdit", jsonNode.get("settingMovieTypeEdit").asInt());
        }
        if (jsonNode.get("settingMovieTypeDelete").asInt() > 0) {
            authorization.setInt("settingMovieTypeDelete", jsonNode.get("settingMovieTypeDelete").asInt());
        }
//        5
        if (jsonNode.get("settingMovieSubTypeRead").asInt() > 0) {
            authorization.setInt("settingMovieSubTypeRead", jsonNode.get("settingMovieSubTypeRead").asInt());
        }
        if (jsonNode.get("settingMovieSubTypeCreate").asInt() > 0) {
            authorization.setInt("settingMovieSubTypeCreate", jsonNode.get("settingMovieSubTypeCreate").asInt());
        }
        if (jsonNode.get("settingMovieSubTypeEdit").asInt() > 0) {
            authorization.setInt("settingMovieSubTypeEdit", jsonNode.get("settingMovieSubTypeEdit").asInt());
        }
        if (jsonNode.get("settingMovieSubTypeDelete").asInt() > 0) {
            authorization.setInt("settingMovieSubTypeDelete", jsonNode.get("settingMovieSubTypeDelete").asInt());
        }
//       6
        if (jsonNode.get("settingClientSettingRead").asInt() > 0) {
            authorization.setInt("settingClientSettingRead", jsonNode.get("settingClientSettingRead").asInt());
        }
        if (jsonNode.get("settingClientSettingCreate").asInt() > 0) {
            authorization.setInt("settingClientSettingCreate", jsonNode.get("settingClientSettingCreate").asInt());
        }
        if (jsonNode.get("settingClientSettingEdit").asInt() > 0) {
            authorization.setInt("settingClientSettingEdit", jsonNode.get("settingClientSettingEdit").asInt());
        }
        if (jsonNode.get("settingClientSettingDelete").asInt() > 0) {
            authorization.setInt("settingClientSettingDelete", jsonNode.get("settingClientSettingDelete").asInt());
        }
        return authorization;
    }

    private boolean checkUserName(String userName) {
        try {
            JsonObject userInput = new JsonObject();
            userInput.setString("userName", userName);
            JsonObject userInfo = this.userService.loadUserByName(userInput);
            if (userInfo != null ) {
                return true;
            }
            return false;
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception checkUserName:", e);
            return false;
        }
    }
}
