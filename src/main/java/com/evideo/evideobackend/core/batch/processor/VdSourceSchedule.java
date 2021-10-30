//package com.evideo.evideobackend.core.batch.processor;
//
//import com.evideo.evideobackend.core.batch.task.MyTaskOne;
//import com.evideo.evideobackend.core.batch.task.MyTaskTwo;
//import com.evideo.evideobackend.core.batch.task.VdSourceTask;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableBatchProcessing
//public class VdSourceSchedule {
//    @Autowired
//    private JobBuilderFactory jobs;
//
//    @Autowired
//    private StepBuilderFactory steps;
//
//    @Bean
//    public Step stepVdSourceTask(){
//        return steps.get("stepVdSourceTask")
//                .tasklet(new VdSourceTask())
//                .build();
//    }
//
//    @Bean
//    public Job vdSourceTaskJob(){
//        return jobs.get("vdSourceTaskJob")
//                .incrementer(new RunIdIncrementer())
//                .start(stepVdSourceTask())
//                .build();
//    }
//}
