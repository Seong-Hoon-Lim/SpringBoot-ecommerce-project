CREATE TABLE user_master
(
    user_master_id            BIGINT AUTO_INCREMENT NOT NULL COMMENT '사용자 마스터 식별 키',
    user_password             VARCHAR(255)          NOT NULL COMMENT '사용자 비밀번호',
    user_type                 VARCHAR(50)           NOT NULL COMMENT '사용자 유형',
    user_status               VARCHAR(50)           NOT NULL COMMENT '사용자 상태',
    user_join_type            VARCHAR(50)           NOT NULL COMMENT '사용자 가입 유형(EMAIL/SNS)',
    user_role                 VARCHAR(50)           NOT NULL COMMENT '사용자 Role',
    oauth2_type               VARCHAR(100)          NULL     DEFAULT NULL  COMMENT 'SNS 유형',
    oauth2_id                 VARCHAR(100)          NULL     DEFAULT NULL  COMMENT 'SNS 계정 식별키',
    user_uuid                 VARCHAR(255)          NULL     DEFAULT NULL  COMMENT '사용자 uuid Key',
    user_password_wrong_count INT                   NOT NULL COMMENT '사용자 패스워드 틀린 횟수',
    user_authentication_id    BIGINT                NOT NULL COMMENT '사용자 인증번호 식별 키',
    join_datetime             DATETIME              NOT NULL DEFAULT NOW() COMMENT '가입 일자',
    withdrawal_datetime       DATETIME              NULL     DEFAULT NULL  COMMENT '탈퇴 일자',
    created_datetime          DATETIME              NOT NULL DEFAULT NOW(),
    created_by                VARCHAR(100)          NOT NULL,
    updated_datetime          DATETIME              NULL     DEFAULT NULL,
    updated_by                VARCHAR(100)          NOT NULL,
    PRIMARY KEY (user_master_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 마스터 테이블'
;

CREATE TABLE user_master_detail
(
    user_master_detail_id     BIGINT AUTO_INCREMENT NOT NULL COMMENT '사용자 마스터 상세 식별 키',
    user_master_id            BIGINT                NOT NULL COMMENT '사용자 마스터 식별 키',
    user_account              VARCHAR(100)          NOT NULL COMMENT '사용자 계정',
    nickname                  VARCHAR(100)          NOT NULL COMMENT '닉네임',
    email                     VARCHAR(100)          NOT NULL COMMENT '이메일',
    name                      VARCHAR(100)          NOT NULL COMMENT '이름',
    birth                     VARCHAR(8)            NOT NULL COMMENT '생년월일',
    mobile                    VARCHAR(14)           NOT NULL COMMENT '휴대폰번호',
    telephone                 VARCHAR(14)           NOT NULL COMMENT '유선번호',
    gender                    char(1)               NOT NULL COMMENT '성별(M/W)',
    postal_code               VARCHAR(100)          NOT NULL COMMENT '우편번호',
    state                     VARCHAR(100)          NOT NULL COMMENT '도',
    city                      VARCHAR(100)          NOT NULL COMMENT '시',
    district                  VARCHAR(100)          NOT NULL COMMENT '구(군)',
    sub_district              VARCHAR(100)          NOT NULL COMMENT '동',
    address_detail            VARCHAR(100)          NOT NULL COMMENT '상세주소',
    created_datetime          DATETIME              NOT NULL DEFAULT NOW(),
    created_by                VARCHAR(100)          NOT NULL,
    updated_datetime          DATETIME              NULL     DEFAULT NULL,
    updated_by                VARCHAR(100)          NOT NULL,
    PRIMARY KEY (user_master_detail_id),
    UNIQUE INDEX `nickname_UNIQUE`  (`nickname` ASC)    VISIBLE,
    UNIQUE INDEX `email_UNIQUE`     (`email` ASC)       VISIBLE,
    UNIQUE INDEX `mobile_UNIQUE`    (`mobile` ASC)      VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 마스터 상세 테이블'
;

CREATE TABLE product_master
(
    product_master_id         BIGINT AUTO_INCREMENT NOT NULL COMMENT '상품 마스터 식별 키',
    user_master_id            BIGINT                NOT NULL COMMENT '사용자 마스터 식별 키',
    product_type              VARCHAR(50)           NOT NULL COMMENT '상품 유형',
    product_status            VARCHAR(50)           NOT NULL COMMENT '상품 상태',
    product_image_id          BIGINT                NOT NULL COMMENT '상품 이미지 식별 키',
    created_datetime          DATETIME              NOT NULL DEFAULT NOW() COMMENT '상품 생성 일자',
    created_by                VARCHAR(100)          NOT NULL,
    uploaded_datetime           DATETIME              NOT NULL DEFAULT NOW() COMMENT '상품 업로드 일자',
    uploaded_by                VARCHAR(100)          NOT NULL,
    updated_datetime          DATETIME              NULL     DEFAULT NULL,
    updated_by                VARCHAR(100)          NOT NULL,
    removed_datetime           DATETIME              NULL     DEFAULT NULL  COMMENT '상품 삭제 일자',
    removed_by                VARCHAR(100)          NOT NULL,
    PRIMARY KEY (product_master_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품 마스터 테이블'
;

CREATE TABLE product_master_detail
(
    product_master_detail_id  BIGINT AUTO_INCREMENT NOT NULL COMMENT '상품 마스터 상세 식별 키',
    product_master_id         BIGINT                NOT NULL COMMENT '상품 마스터 식별 키',
    `price`      INT          NOT NULL,
    `stock`      INT          NOT NULL DEFAULT 0,
    `hit`        INT          NOT NULL DEFAULT 0,
    `content`    TEXT         NOT NULL,
    `imgName`    VARCHAR(255) NOT NULL,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `createdBy`  VARCHAR(100) NOT NULL,
    `updateDate` DATETIME     NULL     DEFAULT NULL,
    `updatedBy`  VARCHAR(100) NOT NULL,
    PRIMARY KEY (item_master_id)
);

CREATE TABLE kittopmall.cart
(
    `cartId`    BIGINT       NOT NULL AUTO_INCREMENT,
    `userEmail` VARCHAR(100) NOT NULL,
    `itemId`    BIGINT       NOT NULL,
    PRIMARY KEY (`cartId`),
    INDEX `cart_userEmail_FK_idx` (`userEmail` ASC) VISIBLE,
    INDEX `cart_itemId_FK_idx` (`itemId` ASC) VISIBLE,
    CONSTRAINT `cart_userEmail_FK`
        FOREIGN KEY (`userEmail`)
            REFERENCES `kittopmall`.`user` (`email`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `cart_itemId_FK`
        FOREIGN KEY (`itemId`)
            REFERENCES `item` (`itemId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    UNIQUE INDEX `itemId_UNIQUE` (`itemId` ASC) VISIBLE
);

CREATE TABLE kittopmall.orders
(
    `orderId`         BIGINT       NOT NULL AUTO_INCREMENT,
    `itemId`          BIGINT       NOT NULL,
    `count`           INT          NOT NULL,
    `userEmail`       VARCHAR(50)  NOT NULL,
    `ordererAddress`  VARCHAR(100) NOT NULL,
    `request`         VARCHAR(100),
    `orderer`         VARCHAR(255) NOT NULL,
    `receiver`        VARCHAR(50)  NOT NULL,
    `ordererContact`  VARCHAR(100) NOT NULL,
    `receiverContact` VARCHAR(100) NOT NULL,
    `createdAt`       DATETIME     NOT NULL DEFAULT now(),
    `updateDate`      DATETIME     NULL     DEFAULT NULL,
    `status`          VARCHAR(10)  NOT NULL,
    `totalPrice`      INT          NOT NULL,
    `payment`         VARCHAR(100) NOT NULL,
    `tossOrderId`     VARCHAR(100) NOT NULL,
    `tossMethod`      VARCHAR(10)  NOT NULL,
    `tossBank`        VARCHAR(10)  NOT NULL,
    `reviewId`        BIGINT       NULL,
    `questionId`      BIGINT       NULL,
    PRIMARY KEY (`orderId`)
);

CREATE TABLE kittopmall.question
(
    `questionId` BIGINT       NOT NULL AUTO_INCREMENT,
    `category`   VARCHAR(10)  NOT NULL,
    `title`      VARCHAR(50)  NOT NULL,
    `content`    VARCHAR(500) NOT NULL,
    `userEmail`  VARCHAR(50)  NOT NULL,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `createdBy`  VARCHAR(100) NOT NULL,
    `updateDate` DATETIME     NULL     DEFAULT NULL,
    `updatedBy`  VARCHAR(100) NOT NULL,
    `orderId`    bigint       null,
    PRIMARY KEY (`questionId`)
);

create table kittopmall.review
(
    `reviewId`   bigint auto_increment
        primary key,
    `title`      varchar(50)  not null,
    `content`    varchar(500) not null,
    `userEmail`  varchar(50)  not null,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `createdBy`  VARCHAR(100) NOT NULL,
    `updateDate` DATETIME     NULL     DEFAULT NULL,
    `updatedBy`  VARCHAR(100) NOT NULL,
    `itemId`     bigint       not null,
    `orderId`    bigint       null,
    constraint review_itemId_FK
        foreign key (itemId) references item (itemId)
            on update cascade on delete cascade
)
    charset = utf8mb3;

create index review_itemId_FK_idx
    on kittopmall.review (itemId);

CREATE TABLE kittopmall.comment
(
    commentId    BIGINT       NOT NULL AUTO_INCREMENT,
    `content`    VARCHAR(500) NOT NULL,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `questionId` BIGINT       NOT NULL,
    PRIMARY KEY (commentId),
    INDEX `comment_questionId_FK_idx` (`questionId` ASC) VISIBLE,
    CONSTRAINT `comment_questionId_FK`
        FOREIGN KEY (`questionId`)
            REFERENCES question (`questionId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
