INSERT INTO grade (id, name, point_rate, upgrade_standard_amount, created_at, updated_at, deleted_at)
VALUES
    (1, 'a', 0, 0, NOW(), NOW(), NULL),
    (2, 'b', 1, 20000, NOW(), NOW(), NULL);

INSERT INTO member (id, login_id, password, name, phone, email, birth_date,
                    last_login_at, platform_type, created_at, updated_at, deleted_at)
VALUES
    (1, 'user1', 'pw1', '테스트유저', '01012341234', 'user1@test.com', '1998-01-01',
     NOW(), 'LOCAL', NOW(), NOW(), NULL);

INSERT INTO member_grade_history (id, member_id, grade_id, reason, created_at, updated_at, deleted_at)
VALUES
    (101, 1, 1, '초기등급', NOW(), NOW(), NULL);

INSERT INTO `order` (id, member_id, order_number, orderer_name, orderer_phone, orderer_email,
                     recipient_name, recipient_phone, zipcode, street_name, detail,
                     delivery_request, delivery_at, delivery_fee, total_price, status,
                     created_at, updated_at, deleted_at)
VALUES
    (500, 1, 'ORD-0001', '테스트유저', '01012341234', 'user1@test.com',
     '수령인', '01012345678', '12345', '테스트 주소', '상세주소',
     '요청사항 없음', DATEADD('DAY', -3, NOW()), 3000, 30000, 'DELIVERED',
     NOW(), NOW(), NULL);

INSERT INTO payment (id, order_number, payment_type, payment_key, payment_status,
                     total_amount, point_used, created_at, updated_at, deleted_at)
VALUES
    (900, 'ORD-0001', 'TOSS', 'pk_test', 'APPROVED',
     30000, 0, NOW(), NOW(), NULL);
