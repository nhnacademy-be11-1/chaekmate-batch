package com.nhnacademy.chaekmatebatch.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.nhnacademy.chaekmatebatch.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Table(name = "member_grade_history")
@SQLRestriction("deleted_at is null")
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE member_grade_history SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Entity
public class MemberGradeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @Column(length = 100, nullable = false)
    private String reason;

    public MemberGradeHistory(Member member, Grade grade, String reason) {
        this.member = member;
        this.grade = grade;
        this.reason = reason;
    }

    public static MemberGradeHistory create(Member memberRef, Grade newGrade) {
        return new MemberGradeHistory(memberRef,newGrade,"배치등급변경");
    }
}
