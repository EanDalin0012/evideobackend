package com.evideo.evideobackend.unsecur.rest;

import com.evideo.evideobackend.core.rest.AuthenticationRest;
import com.evideo.evideobackend.core.util.Base64ImageUtil;
import com.evideo.evideobackend.core.util.SystemUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
@RequestMapping(value = "/unsecur/api/resource")
public class Testing {
    static Logger log = Logger.getLogger(Testing.class.getName());
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    @Inject
    private Environment env;

    @PostMapping(value = "/post")
    public void testing(@RequestBody JsonNode jsonNode) {
        try {
            String base64 = jsonNode.get("bas64").asText();
            String name = jsonNode.get("name").asText();

            String[] strings = base64.split(",");
            String arr[] = strings[0].split("/");
            String arr1[] = arr[1].split(";");
            String extension = arr1[0];
//            data:video/mp4;base64
//            data:image/png;base64
//            String extension;
//            switch (strings[0]) {//check image's extension
//                case "data:image/jpeg;base64":
//                    extension = "jpeg";
//                    break;
//                case "data:image/png;base64":
//                    extension = "png";
//                    break;
//                default://should write cases for more images types
//                    extension = "jpg";
//                    break;
//            }

            String path1 = "/uploads/images/";
//            String basePath = Base64ImageUtil.decodeToImage(path1, base64, "22292.", extension);

            String mkdir = env.getProperty("vd.path") +"/"+ path1;

            File f = new File(mkdir);
            if (!f.exists()) {
                log.info("path exits");
                f.mkdirs();
            }

            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);

            FileOutputStream fout= new FileOutputStream(mkdir+name+"."+extension);

            fout.write(data);
            fout.close();

//            String fullPath = path1 + "/" + name + "" + extension;
//            log.info("full path:" + fullPath);
//            writeByteToImageFile(data, mkdir + "/" + name+"." + extension, extension);

//            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void writeByteToImageFile(byte[] imgBytes, String imgFileName, String extension) throws IOException {
        File imgFile = new File(imgFileName);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
        ImageIO.write(img, extension, imgFile);
    }
}
