package com.evideo.evideobackend.adminlte.schedule;

import java.text.ParseException;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.evideo.evideobackend.adminlte.event.UpdateVdSourceScheduleEvent;
import com.evideo.evideobackend.adminlte.service.impl.VideoSourceLTEServiceImpl;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.dto.JsonObject;


@Component
public class VdSourceSchedule {

	static Logger log = Logger.getLogger(VdSourceSchedule.class.getName());
    private String key;
    
    @Inject
    private ApplicationEventPublisher eventPublisher;
    
    final VideoSourceLTEServiceImpl videoSourceLTEService;
    
    VdSourceSchedule(VideoSourceLTEServiceImpl videoSourceLTEService) {
    	this.videoSourceLTEService = videoSourceLTEService;
    	key = GenerateRandomPassword.key() + "::";
    }
    
	@Scheduled(fixedRate = 60000)
	public void reportCurrentTime() throws ParseException {
		try {
			JsonObject jsonObject = new JsonObject();
			this.eventPublisher.publishEvent(new UpdateVdSourceScheduleEvent(jsonObject));
		}catch (Exception e) {
			// TODO: handle exception
		}
	  
	}
}
