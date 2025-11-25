package com.nhnacademy.chaekmatebatch.batch.grade;


import com.nhnacademy.chaekmatebatch.entity.MemberGradeHistory;
import com.nhnacademy.chaekmatebatch.repository.MemberGradeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeWriter implements ItemWriter<MemberGradeHistory> {

    private final MemberGradeHistoryRepository repo;

    @Override
    public void write(Chunk<? extends MemberGradeHistory> items) {
        repo.saveAll(items);

    }
}
