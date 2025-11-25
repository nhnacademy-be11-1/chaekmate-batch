package com.nhnacademy.chaekmatebatch.repository;

import com.nhnacademy.chaekmatebatch.entity.Grade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByOrderByUpgradeStandardAmountDesc();
}
