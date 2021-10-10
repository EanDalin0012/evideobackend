package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.WriteFileService;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
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
    final FileServiceImplement fileService;

    WriteFileServiceImplement(FileServiceImplement fileService) {
        this.fileService = fileService;
    }

    @Override
    public int writeFile(int userId, String fileName, String extension, String fullPath, String base64) {
        try {
            UUID uuid = UUID.randomUUID();
            String sourceName = uuid + "-"+fileName;
            String path = fullPath + sourceName + "."+extension;

            String[] strings = base64.split(",");
            String mkdir = env.getProperty("vd.path") +"/"+fullPath;
            log.info("WriteFileService writeFile mkdir:"+mkdir);

            File f = new File(mkdir);
            if (!f.exists()) {
                log.info("path exits");
                f.mkdirs();
            }

            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
            FileOutputStream fout= new FileOutputStream(mkdir+sourceName+"."+extension);
            fout.write(data);
            fout.close();

            int id = this.fileService.count();
            String localDate = CurrentDateUtil.get();

            JsonObject jsonObject = new JsonObject();
            jsonObject.setInt("id", id);
            jsonObject.setInt("userId", userId);
            jsonObject.setString("fileName", sourceName);
            jsonObject.setString("fileExtension", extension);
            jsonObject.setString("fileSource", path);
            jsonObject.setString("status", Status.active);
            jsonObject.setString("createAt", localDate);

            int save = this.fileService.create(jsonObject);
            if (save > 0) {
                return id;
            }

        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
