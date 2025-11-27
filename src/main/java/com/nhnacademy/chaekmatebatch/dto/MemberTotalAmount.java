package com.nhnacademy.chaekmatebatch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberTotalAmount {
    Long memberId;
    Long gradeId;
    Long totalAmount;
}
