package com.nhnacademy.chaekmatebatch.batch.grade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.chaekmatebatch.entity.MemberGradeHistory;
import com.nhnacademy.chaekmatebatch.repository.MemberGradeHistoryRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest
@SpringBatchTest

@ActiveProfiles("test")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        StepScopeTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class
})
class GradeBatchIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private MemberGradeHistoryRepository historyRepository;
    @Sql(value = "update_grade_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void gradeBatch_update_grade() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        List<MemberGradeHistory> list = historyRepository.findAll();
        Assertions.assertEquals(2,list.size());
        boolean is = false;
        for (MemberGradeHistory memberGradeHistory : list) {
            if(memberGradeHistory.getGrade().getName().equals("b")){
                is=  true;
            }
        }
        Assertions.assertTrue(is);
    }
}
