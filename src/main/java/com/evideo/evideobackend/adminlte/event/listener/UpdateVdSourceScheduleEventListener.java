package com.evideo.evideobackend.adminlte.event.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.evideo.evideobackend.adminlte.event.UpdateVdSourceScheduleEvent;
import com.evideo.evideobackend.adminlte.service.impl.VideoSourceLTEServiceImpl;
import com.evideo.evideobackend.core.common.GenerateRandomPassword;
import com.evideo.evideobackend.core.constant.Status;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.CurrentDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UpdateVdSourceScheduleEventListener implements ApplicationListener<UpdateVdSourceScheduleEvent>{
	
	static Logger log = Logger.getLogger(UpdateVdSourceScheduleEventListener.class.getName());
    private String key;
    
    final VideoSourceLTEServiceImpl videoSourceLTEService;
    
    UpdateVdSourceScheduleEventListener(VideoSourceLTEServiceImpl videoSourceLTEService) {
    	key = GenerateRandomPassword.key() + "::";
    	this.videoSourceLTEService = videoSourceLTEService;
    }
    
	@Override
	public void onApplicationEvent(UpdateVdSourceScheduleEvent event) {
		log.info(key+"=============== Start UpdateVdSourceScheduleEventListener ==================");
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			Date date = CurrentDateUtil.getLocalDateTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			String dateString = simpleDateFormat.format(date);
			JsonObject jsonObject = new JsonObject();
			jsonObject.setString("schedule", dateString);
			jsonObject.setString("scheduleYN", "Y");
			jsonObject.setString("status", Status.delete);
			
			log.info(key+"Request Inquiry Info :"+mapper.writeValueAsString(jsonObject));
			JsonObjectArray objArr = this.videoSourceLTEService.inquirySchedule(jsonObject);
			log.info(key+"Response Inquiry Info :"+mapper.writeValueAsString(objArr));
			
			if(objArr.size() > 0) {
				for(JsonObject jsonObj: objArr.toListData()) {
					log.info(key + "jsonObj"+mapper.writeValueAsString(jsonObj));
					JsonObject jsonUpdate = new JsonObject();
					jsonUpdate.setInt("id", jsonObj.getInt("id"));
					jsonUpdate.setString("scheduleYN", "N");
					int update = this.videoSourceLTEService.updateSchedule(jsonUpdate);
					log.info(key+"update Schedule Info :"+update);
				}
			}
			
		}catch (Exception e) {
			log.error(key+"UpdateVdSourceScheduleEventListener", e);
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
