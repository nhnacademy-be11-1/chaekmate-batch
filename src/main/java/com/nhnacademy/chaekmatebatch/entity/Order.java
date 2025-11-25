package com.nhnacademy.chaekmatebatch.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.nhnacademy.chaekmatebatch.common.entity.BaseEntity;
import com.nhnacademy.chaekmatebatch.entity.type.OrderStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Table(name = "`order`")
@SQLRestriction("deleted_at is null")
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE `order` SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(unique = true, length = 21, nullable = false)
    private String orderNumber;

    @Column(length = 50, nullable = false)
    private String ordererName;

    @Column(length = 20, nullable = false)
    private String ordererPhone;

    @Column(length = 200, nullable = false)
    private String ordererEmail;

    @Column(length = 50, nullable = false)
    private String recipientName;

    @Column(length = 20, nullable = false)
    private String recipientPhone;

    @Column(length = 5, nullable = false)
    private String zipcode;

    @Column(length = 200, nullable = false)
    private String streetName;

    @Column(length = 100, nullable = false)
    private String detail;

    @Column(length = 255)
    private String deliveryRequest;

    @Column(nullable = false)
    private LocalDate deliveryAt;

    @Column(nullable = false)
    private int deliveryFee;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private OrderStatusType status;

    @Column(nullable = false)
    private long totalPrice;
}
