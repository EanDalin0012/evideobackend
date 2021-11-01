package com.evideo.evideobackend.adminlte.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.evideo.evideobackend.adminlte.event.UpdateVdSourceScheduleEvent;
import com.evideo.evideobackend.adminlte.service.implement.VideoSourceLTEServiceImplement;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.util.CurrentDateUtil;


@Component
public class VdSourceSchedule {

	static Logger log = Logger.getLogger(VdSourceSchedule.class.getName());
    private String key;
    
    @Inject
    private ApplicationEventPublisher eventPublisher;
    
    final VideoSourceLTEServiceImplement videoSourceLTEService;
    
    VdSourceSchedule(VideoSourceLTEServiceImplement videoSourceLTEService) {
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
