

CREATE TABLE user_master
(
    user_master_id            BIGINT AUTO_INCREMENT NOT NULL COMMENT '사용자 마스터 식별 키',
    user_password             VARCHAR(255)          NOT NULL COMMENT '사용자 비밀번호',
    user_type                 VARCHAR(50)           NOT NULL COMMENT '사용자 유형',
    user_status               VARCHAR(50)           NOT NULL COMMENT '사용자 상태',
    user_join_type            VARCHAR(50)           NOT NULL COMMENT '사용자 가입 유형',
    user_role                 VARCHAR(50)           NOT NULL COMMENT '사용자 Role',
    user_uuid                 VARCHAR(255)          NOT NULL COMMENT '사용자 uuid Key',
    user_password_wrong_count INT                   NOT NULL COMMENT '사용자 패스워드 틀린 횟수',
    user_authentication_id    BIGINT                NOT NULL COMMENT '사용자 인증번호 식별 키',
    createdAt                 DATETIME              NOT NULL DEFAULT NOW(),
    createdBy                 VARCHAR(100)          NOT NULL,
    updateDate                DATETIME              NULL     DEFAULT NULL,
    updatedBy                 VARCHAR(100)          NOT NULL
);

CREATE TABLE kittopmall.user
(
    `userId`     BIGINT       NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(100) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `nickname`   VARCHAR(100) NOT NULL,
    `name`       VARCHAR(100) NOT NULL,
    `birth`      VARCHAR(100) NOT NULL,
    `gender`     VARCHAR(10)  NOT NULL,
    `addr`       VARCHAR(100) NOT NULL,
    `phone`      VARCHAR(100) NOT NULL,
    `provider`   VARCHAR(100) NULL,
    `providerId` VARCHAR(100) NULL,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `createdBy`  VARCHAR(100) NOT NULL,
    `updateDate` DATETIME     NULL     DEFAULT NULL,
    `updatedBy`  VARCHAR(100) NULL,
    `role`       VARCHAR(50)  NOT NULL,
    PRIMARY KEY (`userId`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
    UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE
);

CREATE TABLE kittopmall.item
(
    `itemId`     BIGINT       NOT NULL AUTO_INCREMENT,
    `category`   VARCHAR(50)  NOT NULL,
    `itemName`   VARCHAR(100) NOT NULL,
    `price`      INT          NOT NULL,
    `stock`      INT          NOT NULL DEFAULT 0,
    `hit`        INT          NOT NULL DEFAULT 0,
    `content`    TEXT         NOT NULL,
    `imgName`    VARCHAR(255) NOT NULL,
    `createdAt`  DATETIME     NOT NULL DEFAULT now(),
    `createdBy`  VARCHAR(100) NOT NULL,
    `updateDate` DATETIME     NULL     DEFAULT NULL,
    `updatedBy`  VARCHAR(100) NOT NULL,
    PRIMARY KEY (`itemId`)
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