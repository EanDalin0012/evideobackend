package com.evideo.evideobackend.core.rest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.service.implement.FileServiceImplement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FilesController {
	static Logger log = Logger.getLogger(FilesController.class.getName());
	private static String UPLOADED_FOLDER = "D:\\my-vd//";
	
	private final FileServiceImplement fileService;

    @Autowired
    public FilesController(FileServiceImplement fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<JsonObject> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
    	log.info("file Info start .....");
        fileService.save(file);
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("add", "add");
        return ResponseEntity.status(HttpStatus.OK)
                             .body(jsonObject);
    }
    
    @PostMapping("/video/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
    
}
