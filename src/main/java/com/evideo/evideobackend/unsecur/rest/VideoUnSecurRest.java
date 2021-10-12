package com.evideo.evideobackend.unsecur.rest;


import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.unsecur.service.implement.VideoSourceServiceImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/unsecur/api/resource/vd")
public class VideoUnSecurRest {
    static Logger log = Logger.getLogger(VideoUnSecurRest.class.getName());

    @Inject
    private Environment env;

    final VideoSourceServiceImplement videoSourceServiceImplement;

    VideoUnSecurRest(VideoSourceServiceImplement videoSourceServiceImplement) {
        this.videoSourceServiceImplement = videoSourceServiceImplement;
    }

    @GetMapping(value = "/v0/vdSource/{id}")
    public ResponseEntity readVideoResource(@PathVariable("id") int id) throws Exception {
        log.info("Resource Video Id :"+id);
        String sourcePath = this.env.getProperty("vd.path");
        try {
            JsonObject input = new JsonObject();
            input.setInt("id", id);
            JsonObject jsonObject = this.videoSourceServiceImplement.inquirySourceVideo(input);
            String fullPath = sourcePath + jsonObject.getString("fileSource");
            log.info("Full Path :"+fullPath);
            File file = new File(fullPath);
            InputStream inputStream = new FileInputStream(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept-Ranges", "bytes");
            headers.add("Content-Type", "video/mp4");
            headers.add("Content-Range", "bytes 99-102517839845");
            headers.add("Content-Length", String.valueOf(file.length()));
            return new ResponseEntity(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
       return null;
    }

    @PostMapping(value = "/v0/vd")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) {

        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Client Request Data :" + objectMapper.writeValueAsString(jsonObject));

            int vdId = jsonObject.getInt("vdId");
            if (vdId <= 0) {
                header.setResponseCode("invalidVdId");
                header.setResponseMessage("Invalid Video Id");
                responseData.setResult(header);
                return responseData;
            }

            JsonObjectArray array = this.videoSourceServiceImplement.inquiryVideoSourceByVDId(vdId);
            responseData.setBody(array);
            responseData.setResult(header);
            log.info("Response to client data : "+objectMapper.writeValueAsString(responseData));
            return responseData;

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(MessageCode.exception);
            e.printStackTrace();
        }

        header.setResponseCode(StatusCode.notFound);
        header.setResponseMessage(MessageCode.notFound);
        responseData.setResult(header);
        return responseData;
    }
}
