package com.evideo.evideobackend.core.batch.processor;

import com.evideo.evideobackend.core.batch.task.MyTaskOne;
import com.evideo.evideobackend.core.batch.task.MyTaskTwo;
import com.evideo.evideobackend.core.batch.task.VdSourceTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step stepOne(){
        return steps.get("stepOne")
                .tasklet(new MyTaskOne())
                .build();
    }

    @Bean
    public Step stepTwo(){
        return steps.get("stepTwo")
                .tasklet(new MyTaskTwo())
                .build();
    }

    @Bean
    public Job demoJob(){
        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
//                .next(stepTwo())
                .build();
    }

//    @Bean
//    public Step stepVdSourceTask(){
//        return steps.get("stepVdSourceTask")
//                .tasklet(new VdSourceTask())
//                .build();
//    }
//
//    @Bean
//
//    public Job vdSourceTaskJob(){
//        return jobs.get("vdSourceTaskJob")
//                .incrementer(new RunIdIncrementer())
//                .start(stepVdSourceTask())
//                .build();
//    }

}
