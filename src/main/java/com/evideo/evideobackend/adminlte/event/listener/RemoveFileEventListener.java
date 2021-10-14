package com.evideo.evideobackend.adminlte.event.listener;

import com.evideo.evideobackend.adminlte.event.RemoveFileEvent;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.events.listeners.HistoryUserLoginEventListener;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.implement.FileServiceImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class RemoveFileEventListener implements ApplicationListener<RemoveFileEvent> {
    static Logger log = Logger.getLogger(HistoryUserLoginEventListener.class.getName());

    @Inject
    private Environment env;

    final FileServiceImplement fileService;
    RemoveFileEventListener(FileServiceImplement fileService) {
        this.fileService = fileService;
    }

    @Override
    public void onApplicationEvent(RemoveFileEvent event) {
        try {
            log.info("================== Start Remove File Event Listener ===============");
            ObjectMapper objectMapper =new ObjectMapper();
            JsonObject info = (JsonObject) event.getSource();
            JsonObject input = new JsonObject();
            input.setInt("id", info.getInt("id"));
            log.info("RemoveFileEventListener info:"+objectMapper.writeValueAsString(info));

            JsonObject jsonObject = this.fileService.readBySourceId(input);
            log.info("Inquiry info:"+objectMapper.writeValueAsString(jsonObject));

            if (jsonObject != null) {
                Path path = Paths.get(env.getProperty("vd.path") + jsonObject.getString("fileSource"));
                String filePath = env.getProperty("vd.path") + jsonObject.getString("fileSource");
                log.info("filePath:"+path);
                File file = path.toFile();
                log.info("Exit:"+file.exists());
                if (file.exists()) {
                    int delete = this.fileService.delete(input);
                    log.info("RemoveFileEventListener Success:" + delete);
                    if (delete > 0) {
                        log.info("RemoveFileEventListener Success");
                    }
                    file.delete();
                }

            }
            log.info("================== End Remove File Event Listener ===============");

        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }

    }
}
