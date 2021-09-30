package com.evideo.evideobackend.core.events.listeners;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.events.WingNotifyEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WingNotifyEventListener implements ApplicationListener<WingNotifyEvent> {

    @Override
    public void onApplicationEvent(WingNotifyEvent event) {
        try {
            Thread.sleep(10000);
            System.out.println("WingNotifyEventListener Work");
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("event"+ event.getSource()+""+objectMapper.writeValueAsString(event));
            JsonObject json = (JsonObject) event.getSource();
            System.out.println("getSource "+ event.getSource());
            System.out.println("event "+ event);
            System.out.println("json "+ objectMapper.writeValueAsString(json));
        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
