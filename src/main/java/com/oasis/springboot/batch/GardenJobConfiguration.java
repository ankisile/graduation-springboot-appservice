package com.oasis.springboot.batch;

import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.service.GardenOpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class GardenJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
    private final GardenRepository gardenRepository;
    private final GardenOpenApiService gardenOpenApiService;

    @Bean
    public Job gardenScrappingJob(){
        return jobBuilderFactory.get("gardenJob")
                  .start(gardenInitStep())
                  .next(gardenScrappingJobStep())
                  .build();
    }


    @Bean
    public Step gardenInitStep(){
        return stepBuilderFactory.get("gardenInitStep")
            .tasklet((contribution, chunkContext) -> {
                gardenRepository.deleteAll();
                return RepeatStatus.FINISHED;
            }).build();
    }


    @Bean
    public Step gardenScrappingJobStep(){
        return stepBuilderFactory.get("gardenScrapStep")
                .tasklet((contribution, chunkContext) -> {
                    gardenOpenApiService.saveOpenApiGardenList();
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
