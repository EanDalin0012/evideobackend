package com.evideo.evideobackend.unsecur.rest;

import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.unsecur.service.implement.ImageReaderServiceImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/unsecur/api/image/reader")
public class ImageReaderRest {
    static Logger log = Logger.getLogger(ImageReaderRest.class.getName());
    private String key;
    @Inject
    private Environment env;

    final ImageReaderServiceImplement imageReaderService;
    ImageReaderRest(ImageReaderServiceImplement imageReaderService) {
        this.imageReaderService = imageReaderService;
        key = GenerateRandomPassword.key() + "::";
    }

    @GetMapping("/v0/read/{resourceId}")
    public ResponseEntity<byte[]> resourcesImage(@PathVariable("resourceId") int resourceId) {
        HttpHeaders headers = new HttpHeaders();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonObject input = new JsonObject();
            input.setInt("id", resourceId);
            JsonObject imgInfo = this.imageReaderService.inquiryResourcesID(input);

//            log.info(key+"file info values: " + objectMapper.writeValueAsString(imgInfo));

            if (imgInfo != null) {
                String path = imgInfo.getString("fileSource");
                String filepath = env.getProperty("vd.path")  + path;
                String fileExt = imgInfo.getString("fileExtension");
//                log.info(key+"full path : " + filepath);
//                log.info(key+"full extension : " + fileExt);
                File file = new File(filepath);


                if (file.exists()) {
                    InputStream inputStream = new FileInputStream(file);
                    if (fileExt.equalsIgnoreCase("JPG")) {
                        headers.setContentType(MediaType.IMAGE_JPEG);
                    } else if (fileExt.equalsIgnoreCase("PNG")) {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    } else {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    }
                    headers.setContentLength(file.length());
                    return new ResponseEntity(new InputStreamResource(inputStream), headers, HttpStatus.OK);
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            log.error(key+"error read image", e);
        }
        return null;
    }

    @GetMapping("/v0/read1/{resourceId}")
    public ResponseEntity<byte[]> resourcesImage1(@PathVariable("resourceId") int resourceId) throws IOException {
        byte bytes[] = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonObject input = new JsonObject();
            input.setInt("id", resourceId);
            JsonObject imgInfo = this.imageReaderService.inquiryResourcesID(input);

            log.info("file info values: " + objectMapper.writeValueAsString(imgInfo));

            if (imgInfo != null) {
                String path = imgInfo.getString("fileSource");
                String filepath = env.getProperty("vd.path")  + path;
                String fileExt = imgInfo.getString("fileExtension");
                File file = new File(filepath);
                if (file.exists()) {
                    InputStream targetStream = new FileInputStream(file);
                    bytes = IOUtils.toByteArray(targetStream);
                    if (fileExt.equalsIgnoreCase("JPG")) {
                        headers.setContentType(MediaType.IMAGE_JPEG);
                    } else if (fileExt.equalsIgnoreCase("PNG")) {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    } else {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    }
                    headers.setContentLength(bytes.length);
                    return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            log.error("error read image", e);
        }
        return null;
    }

}
