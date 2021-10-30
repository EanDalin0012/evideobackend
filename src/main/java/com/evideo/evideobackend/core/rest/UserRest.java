package com.evideo.evideobackend.core.rest;

import com.evideo.evideobackend.adminlte.event.RemoveFileEvent;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dao.AuthorizationDao;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    static Logger log = Logger.getLogger(UserRest.class.getName());
    private static String key;
    @Inject
    private ApplicationEventPublisher eventPublisher;

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

    @GetMapping(value = "/v0/read")
    public ResponseData<JsonObjectArray> read(@RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        log.info(key+"============= Start VideoRest Read ============");

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObjectArray> responseData = new ResponseData<JsonObjectArray>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.setString("status", Status.delete);
            jsonObject.setInt("id", userId);
            JsonObjectArray restData = this.userService.read(jsonObject);
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

    @PostMapping(value = "/v0/checkUserName")
    public ResponseData<JsonObject> checkUserName(@RequestBody JsonNode jsonNode, @RequestParam("lang") String lang, @RequestParam("date") String date) {
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            String userName = jsonNode.get("userName").asText();
            if (checkUserName(userName) == true) {
                header.setResponseCode(StatusCode.Found);
                header.setResponseMessage(StatusCode.Found);
                responseData.setResult(header);
                return responseData;
            }
            header.setResponseMessage("validUserName");
            responseData.setResult(header);
        }catch (Exception e) {
            log.error(key+"Exception :", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        return responseData;
    }


    @PostMapping(value = "/v0/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
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
            
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/loadUserById")
    public ResponseData<JsonObject> loadUserByUserId(@RequestBody JsonNode jsonNode, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, ValidatorException {
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonNode));
            int userId = jsonNode.get("userId").asInt();
            if(userId > 0) {
                JsonObject user = new JsonObject();
                user.setInt("userId", userId);
                JsonObject userData = this.userService.authorizationByUserId(user);
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
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/create")
    public ResponseData<JsonObject> create(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
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
            String address  = jsonNode.get("address").asText();
            int sourceId = jsonNode.get("sourceId").asInt();
            int id          = this.userService.count();


            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordEncoder  = bCryptPasswordEncoder.encode(password);

            int resourceId = 0;
            if (checkUserName(userName) == true) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("userReadyHave");
                responseData.setResult(header);
                return responseData;
            } else if (sourceId <= 0) {
           	 header.setResponseCode(StatusCode.NotFound);
             header.setResponseMessage("invalidFileImage");
             responseData.setResult(header);
             return responseData;
            }
            
            String localDate = CurrentDateUtil.get();
            JsonObject user = new JsonObject();
            user.setInt("id", id);
            user.setInt("roleId", roleId);
            user.setString("fullName", fullName);

            user.setString("dateBirth", dateBirth);
            user.setString("phoneNumber", phoneNumber);
            user.setString("address", address);
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
            if(arrayNode.isArray()) {
                for(JsonNode json : arrayNode) {
                    JsonObject authority = new JsonObject();
                    authority.setInt("userId", user.getInt("id"));
                    authority.setInt("authorityId", json.get("id").asInt());
                    log.info(key + "Authority Info :"+objectMapper.writeValueAsString(authority));
                    this.authorizationService.addAuthorizationAccess(authority);
                }
            }

            if (save > 0) {
                transactionManager.commit(transactionStatus);
                responseData.setResult(header);
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

    @PostMapping(value = "/v0/update")
    public ResponseData<JsonObject> update(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            String fullName = jsonNode.get("fullName").asText();
            String gender   = jsonNode.get("gender").asText();
            String dateBirth = jsonNode.get("dateBirth").asText();
            String phoneNumber = jsonNode.get("phoneNumber").asText();
            String address  = jsonNode.get("address").asText();
            String userName = jsonNode.get("userName").asText();
            String remark   = jsonNode.get("remark").asText();
            int roleId      = jsonNode.get("roleId").asInt();
            int id          = jsonNode.get("id").asInt();
            boolean isSelectedFile = jsonNode.get("selectedFile").asBoolean();

            int resourceId = jsonNode.get("resourceId").asInt();;
            if (isSelectedFile) {
                JsonNode fileInf = jsonNode.get("fileInfo");
                log.info(key + "VideoRest jsonObject :"+objectMapper.writeValueAsString(fileInf).substring(0,30));
                String fileBits = fileInf.get("fileBits").asText();
                String fileName = fileInf.get("fileName").asText();
                String fileExtension = fileInf.get("fileExtension").asText();

                if (fileInf == null) {
                    header.setResponseCode(StatusCode.NotFound);
                    header.setResponseMessage("invalidFileImage");
                    responseData.setResult(header);
                    return responseData;
                } else if (fileInf != null) {
                    if (fileBits.equals("")) {
                        header.setResponseCode(StatusCode.NotFound);
                        header.setResponseMessage("invalidFileImage");
                        responseData.setResult(header);
                        return responseData;
                    } else if (fileName.equals("")) {
                        header.setResponseCode(StatusCode.NotFound);
                        header.setResponseMessage("invalidFileImage");
                        responseData.setResult(header);
                        return responseData;
                    } else if ( fileExtension.equals("")) {
                        header.setResponseCode(StatusCode.NotFound);
                        header.setResponseMessage("invalidFileImage");
                        responseData.setResult(header);
                        return responseData;
                    }
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
            user.setString("address", address);
            user.setString("remark", remark);
            user.setInt("resourceId", resourceId);
            user.setString("status", Status.modify);
            user.setString("modifyAt", localDate);
            user.setString("gender",gender);
            user.setString("userName", userName);
            user.setInt("userId", userId);
            log.info(key + "User Info :"+objectMapper.writeValueAsString(user));

            int update = this.userService.updateUserInfo(user);

            if (id > 0) {
                JsonObject deletePermission = new JsonObject();
                deletePermission.setInt("userId", user.getInt("id"));
                this.authorizationService.deleteAuthorizationAccessByUserId(deletePermission);
            }

            ArrayNode arrayNode  = (ArrayNode) jsonNode.get("authorizationModule");

            if(arrayNode.isArray()) {
                for(JsonNode json : arrayNode) {
                    JsonObject authority = new JsonObject();
                    authority.setInt("userId", user.getInt("id"));
                    authority.setInt("authorityId", json.get("id").asInt());
                    log.info(key + "Authority Info :"+objectMapper.writeValueAsString(authority));
                    this.authorizationService.addAuthorizationAccess(authority);
                }
            }

            if (update > 0 ) {
                transactionManager.commit(transactionStatus);
                responseData.setResult(header);
                if (isSelectedFile == true) {
                    JsonObject input = new JsonObject();
                    input.setInt("id", jsonNode.get("resourceId").asInt());
                    this.eventPublisher.publishEvent(new RemoveFileEvent(input));
                }
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
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        log.info(key+"Response to Http Client : "+objectMapper.writeValueAsString(responseData));
        return responseData;
    }

    @PostMapping(value = "/v0/delete")
    public ResponseData<JsonObject> delete(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            int userID = jsonNode.get("userId").asInt();
            JsonObject jsonObject = new JsonObject();
            String localDate = CurrentDateUtil.get();
            jsonObject.setInt("userId", userID);
            jsonObject.setString("status", Status.delete);
            jsonObject.setString("modifyAt", localDate);
            int delete = this.userService.deleteUser(jsonObject);
            if (delete > 0) {
                responseData.setResult(header);
                return responseData;
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.NotFound);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/enableStatus")
    public ResponseData<JsonObject> enableStatus(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            int userID = jsonNode.get("userId").asInt();
            boolean enable = jsonNode.get("enable").asBoolean();

            if (userID <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidUserId");
                responseData.setResult(header);
                return responseData;
            }
            JsonObject jsonObject = new JsonObject();
            String localDate = CurrentDateUtil.get();
            jsonObject.setInt("userId", userID);
            jsonObject.setString("status", Status.modify);
            jsonObject.setString("modifyAt", localDate);
            jsonObject.setBoolean("enable", enable);
            int enableStatus = this.userService.enableStatus(jsonObject);
            if (enableStatus > 0) {
                responseData.setResult(header);
                return responseData;
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.NotFound);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        return responseData;
    }
    @PostMapping(value = "/v0/changePassword")
    public ResponseData<JsonObject> changePassword(@RequestBody JsonNode jsonNode, @RequestParam("userId") int userId, @RequestParam("lang") String lang, @RequestParam("date") String date) throws JsonProcessingException, AccessDeniedException {

        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        try {
            int userID = jsonNode.get("userId").asInt();
            String password = jsonNode.get("password").asText();
            if (userID <= 0) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidUserId");
                responseData.setResult(header);
                return responseData;
            } else if (password.equals("")) {
                header.setResponseCode(StatusCode.NotFound);
                header.setResponseMessage("invalidPassword");
                responseData.setResult(header);
                return responseData;
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordEncoder  = bCryptPasswordEncoder.encode(password);

            JsonObject jsonObject = new JsonObject();
            String localDate = CurrentDateUtil.get();
            jsonObject.setInt("userId", userID);
            jsonObject.setString("status", Status.modify);
            jsonObject.setString("modifyAt", localDate);
            jsonObject.setString("password", passwordEncoder);
            int enableStatus = this.userService.changePassword(jsonObject);
            if (enableStatus > 0) {
                responseData.setResult(header);
                return responseData;
            }
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage(MessageCode.NotFound);
        }catch (Exception | ValidatorException e) {
            log.error(key+"Exception", e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        }
        responseData.setResult(header);
        return responseData;
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
