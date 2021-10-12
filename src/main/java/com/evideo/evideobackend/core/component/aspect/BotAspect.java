package com.evideo.evideobackend.core.component.aspect;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.rest.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BotAspect {
    static Logger log = Logger.getLogger(BotAspect.class.getName());

    @Pointcut("execution(* com.evideo.evideobackend.core.rest.UserRest.loadUserByUserName(..)))" )
    // or @Pointcut("execution(* com.evideo.evideobackend.core.rest.UserRest..*(..))" )
    private void execute(){
        System.out.println("BotAspect Component execute");
    }

    @Before("execute()")
    public void beforeExecute() throws InterruptedException {
        System.out.println("BotAspect Component beforeExecute");
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("data", "testing");
//        eventPublisher.publishEvent(new WingNotifyEvent(jsonObject));
    }

    @AfterReturning(pointcut = "execute()", argNames = "request, response", returning = "response")
    public void afterExecute(JoinPoint request, Object response) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("BotAspect afterExecute"+objectMapper.writeValueAsString(response));
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("BotAspect Component afterExecute");
//        eventPublisher.publishEvent(new WingBotEvent(getRequest(request, response)));
    }
}
