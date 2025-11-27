package com.nhnacademy.chaekmatebatch.batch.grade;

import com.nhnacademy.chaekmatebatch.dto.GradeRule;
import com.nhnacademy.chaekmatebatch.dto.MemberTotalAmount;
import com.nhnacademy.chaekmatebatch.entity.Grade;
import com.nhnacademy.chaekmatebatch.entity.Member;
import com.nhnacademy.chaekmatebatch.entity.MemberGradeHistory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeProcessor implements ItemProcessor<MemberTotalAmount, MemberGradeHistory> {
    private final EntityManager em;
    private List<GradeRule> grades;

    @BeforeStep
    public void init(StepExecution stepExecution) {
        this.grades = (List<GradeRule>) stepExecution.getExecutionContext().get("gradeRules");
    }

    @Override
    public MemberGradeHistory process(MemberTotalAmount item) {
        long newGradeId = grades.get(grades.size() - 1).id();
        for (GradeRule grade : grades) {
            if (item.getTotalAmount() >= grade.upgradeAmount()) {
                newGradeId = grade.id();
                break;
            }
        }
        if (newGradeId == item.getGradeId()) {
            return null;
        }
        return MemberGradeHistory.create(
                em.getReference(Member.class, item.getMemberId()),
                em.getReference(Grade.class, newGradeId)
        );
    }
}