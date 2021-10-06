package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.service.WriteFileService;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class WriteFileServiceImplement implements WriteFileService {
    static Logger log = Logger.getLogger(WriteFileServiceImplement.class.getName());

    @Inject
    private Environment env;

    @Override
    public int writeFile(String fileName, String base64) {
        try {
            UUID uuid = UUID.randomUUID();
            String[] strings = base64.split(",");
            String arr[] = strings[0].split("/");
            String arr1[] = arr[1].split(";");
            String extension = arr1[0];
            String path = "/uploads/images/";
            String mkdir = env.getProperty("vd.path") +"/"+ path;
            String sourceName = uuid + "-"+fileName;
            File f = new File(mkdir);
            if (!f.exists()) {
                log.info("path exits");
                f.mkdirs();
            }

            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
            FileOutputStream fout= new FileOutputStream(mkdir+sourceName+"."+extension);
            fout.write(data);
            fout.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
