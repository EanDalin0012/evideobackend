package com.evideo.evideobackend.core.batch.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class VdSourceScheduleTask {
//    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    Job job;


//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//
//        log.info("The time is now {5000}", dateFormat.format(new Date()));
//    }

//    @Scheduled(fixedRate = 6000)
//    public void reportCurrent() {
//
//        log.info("The time is now {6000}", dateFormat.format(new Date()));
//    }
}
