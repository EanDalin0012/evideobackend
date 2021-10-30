package com.evideo.evideobackend.core.rest;

import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.ResponseData;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/file")
public class FileRest {

    @PostMapping("/upload")
    public ResponseData<JsonObject> handleFileUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("fileImageURL") String fileImageURL,
                                                     @RequestParam("userID") String userID) throws Exception {
        ResponseData<JsonObject> responseData = new ResponseData<JsonObject>();
        Header header = new Header(StatusCode.Success, MessageCode.Success);

        try {
            boolean file = multipartFile.isEmpty();
            if (!file) {
                UUID uuid = UUID.randomUUID();
                String resID = uuid.toString();
                InputStream is = null;
                String[] originalFilename = multipartFile.getOriginalFilename().split("\\.(?=[^\\.]+$)");
                is = multipartFile.getInputStream();
                IOUtils.closeQuietly(is);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  responseData;

    }
}
