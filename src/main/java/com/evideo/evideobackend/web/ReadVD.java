package com.evideo.evideobackend.web;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/unsecur/api/read/vd")
public class ReadVD {

    @GetMapping(value = "/v0/{vdID}")
    public ResponseEntity LInputStreamResourceretrieveResource(@PathVariable("vdID") int vdID) throws Exception {
        String path = "G:\\vedio\\flutter\\Afgprogrammer\\Flutter Animation\\Flutter Animation Tutorial  Make Page Transition - day 6.mp4";
        if (vdID >= 1) {
            path = "G:\\vedio\\flutter\\Afgprogrammer\\Flutter Login Page UI Design\\Flutter UI Tutorial  Login Page UI and Animation - day 12.mp4";
        }

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Ranges", "bytes");
        headers.add("Content-Type", "video/mp4");
        headers.add("Content-Range", "bytes 50-102517839845");
        headers.add("Content-Length", String.valueOf(file.length()));
        return new ResponseEntity(new InputStreamResource(inputStream), headers, HttpStatus.OK);

    }
}
