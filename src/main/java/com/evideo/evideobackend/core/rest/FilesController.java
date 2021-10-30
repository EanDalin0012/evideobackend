package com.evideo.evideobackend.core.rest;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.MessageCode;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.constant.StatusCode;
import com.evideo.evideobackend.core.dto.Header;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.ResponseData;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.FileServiceImplement;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/files")
public class FilesController {
	static Logger log = Logger.getLogger(FilesController.class.getName());
	private static String key;
	
	@Inject
    private Environment env;
    
	final FileServiceImplement fileService;
    public FilesController(FileServiceImplement fileService) {
        this.fileService = fileService;
        key = GenerateRandomPassword.key() + "::";
    }

    
    @PostMapping("/video/upload") // //new annotation since 4.3
    public  ResponseData<JsonObject> singleFileUpload(@RequestParam("file") MultipartFile file,  @RequestParam("title") String title,@RequestParam("userId") int userId, RedirectAttributes redirectAttributes) throws ParseException {
    	log.info(key + "FilesController Start");
    	ObjectMapper mapper = new ObjectMapper();
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.Success, MessageCode.Success);
        
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            header.setResponseCode(StatusCode.NotFound);
            header.setResponseMessage("invalidFileInfo");
            responseData.setBody(header);
            responseData.setResult(header);
            return responseData;
        }
        
        try {
        	String mkdir = env.getProperty("vd.path") +"/video/upload/"+title+"/";
        	String originalFilename = file.getOriginalFilename();
        	 
        	log.info(key + "file title : "+title);
        	log.info(key + "original file name :"+originalFilename);
        	 
        	File f = new File(mkdir);
            if (!f.exists()) {
                log.info(key + "path exits");
                f.mkdirs();
            }
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
           
            String extension = "video";
            String[] extensionArr = file.getContentType().split("/");
            if(extensionArr.length > 1) {
            	extension = extensionArr[1];
            }
            
            log.info(key + "file extension :"+extension);
            log.info(key + "file content type :"+file.getContentType());
            log.info(key + "mkdir :"+mkdir);
            
            Path path = Paths.get(mkdir + originalFilename);
            log.info(key + "full path :"+path.toString());
            Files.write(path, bytes);
            
            
            int id = this.fileService.count();
            String localDate = CurrentDateUtil.get();

            JsonObject jsonObject = new JsonObject();
            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setString("fileName", file.getOriginalFilename());
            jsonObject.setString("fileExtension", extension);
            jsonObject.setString("fileSource", path.toString());
            jsonObject.setString("status", Status.active);
            jsonObject.setString("fileType", file.getContentType());
            jsonObject.setString("createAt", localDate);
            
            log.info(key + "jsonObject info :"+mapper.writeValueAsString(jsonObject));
            
            int save = this.fileService.create(jsonObject);
            if (save > 0) {
            	JsonObject json = new JsonObject();
            	json.setInt("videoSourceId", id);
            	responseData.setResult(header);
            	responseData.setBody(json);
            	return responseData;
            }
            

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
            log.info(key + "FilesController End");
            
        } catch (IOException e) {
            e.printStackTrace();
            log.error(key+"Exception error :" , e);
            header.setResponseCode(StatusCode.Exception);
            header.setResponseMessage(StatusCode.Exception);
            if (e.getMessage().equals(MessageCode.Forbidden)) {
                header.setResponseCode(StatusCode.Forbidden);
                header.setResponseMessage(MessageCode.Forbidden);
            }
        } catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        responseData.setBody(header);
        responseData.setResult(header);
        return responseData;
    }
    
}
