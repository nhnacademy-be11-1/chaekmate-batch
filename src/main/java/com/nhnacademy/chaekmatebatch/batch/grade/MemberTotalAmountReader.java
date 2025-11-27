package com.nhnacademy.chaekmatebatch.batch.grade;

import com.nhnacademy.chaekmatebatch.dto.MemberTotalAmount;
import com.nhnacademy.chaekmatebatch.entity.QGrade;
import com.nhnacademy.chaekmatebatch.entity.QMember;
import com.nhnacademy.chaekmatebatch.entity.QMemberGradeHistory;
import com.nhnacademy.chaekmatebatch.entity.QOrder;
import com.nhnacademy.chaekmatebatch.entity.QPayment;
import com.nhnacademy.chaekmatebatch.entity.type.OrderStatusType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberTotalAmountReader extends AbstractPagingItemReader<MemberTotalAmount> {

    private final JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        setPageSize(1000);
        setName("memberTotalAmountReader");
    }

    @Override
    protected void doReadPage() {

        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        QMember member = QMember.member;
        QMemberGradeHistory history = QMemberGradeHistory.memberGradeHistory;
        QGrade grade = QGrade.grade;
        QOrder order = QOrder.order;
        QPayment payment = QPayment.payment;
        List<MemberTotalAmount> list = queryFactory
                .select(Projections.constructor(
                        MemberTotalAmount.class,
                        member.id,
                        grade.id,
                        payment.totalAmount.sum()
                ))
                .from(member)
                .join(history).on(
                        history.member.id.eq(member.id)
                                .and(history.createdAt.eq(
                                        JPAExpressions.select(history.createdAt.max())
                                                .from(history)
                                                .where(history.member.id.eq(member.id))
                                ))
                )
                .join(grade).on(grade.id.eq(history.grade.id))
                .join(order).on(order.member.id.eq(member.id))
                .join(payment).on(payment.orderNumber.eq(order.orderNumber))
                .where(
                        order.status.eq(OrderStatusType.DELIVERED),
                        order.deliveryAt.between(
                                LocalDate.now().minusMonths(3),
                                LocalDate.now()
                        )
                )
                .groupBy(member.id, grade.id)
                .orderBy(member.id.asc())
                .offset((long) getPage() * getPageSize())
                .limit(getPageSize())
                .fetch();
        results.addAll(list);
    }
}
