package com.nhnacademy.chaekmatebatch.batch.grade;

import com.nhnacademy.chaekmatebatch.dto.GradeRule;
import com.nhnacademy.chaekmatebatch.dto.MemberTotalAmount;
import com.nhnacademy.chaekmatebatch.entity.Grade;
import com.nhnacademy.chaekmatebatch.entity.MemberGradeHistory;
import com.nhnacademy.chaekmatebatch.repository.GradeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class GradeBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final MemberTotalAmountReader reader;
    private final GradeProcessor processor;
    private final GradeWriter writer;
    private final GradeRepository gradeRepository;
    @Bean
    public Step gradeStep() {
        return new StepBuilder("gradeStep", jobRepository)
                .<MemberTotalAmount, MemberGradeHistory>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {

                        List<Grade> grades = gradeRepository.findAllByOrderByUpgradeStandardAmountDesc();

                        List<GradeRule> rules = grades.stream()
                                .map(g -> new GradeRule(g.getId(), g.getUpgradeStandardAmount()))
                                .toList();

                        stepExecution.getExecutionContext().put("gradeRules", rules);
                    }
                })
                .build();
    }

    @Bean
    public Job gradeJob() {
        return new JobBuilder("gradeJob", jobRepository)
                .start(gradeStep())
                .build();
    }
}