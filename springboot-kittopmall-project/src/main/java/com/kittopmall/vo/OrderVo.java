package com.kittopmall.vo;


import com.kittopmall.vo.constants.Status;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {

    private Long id;
    private UserVo user;
    private ItemVo item;
    private QuestionVo question;
    private ReviewVo review;

    private Integer count;
    private Status status;
    private Double totalPrice;
    private String ordererAddress;
    private String request;
    private String orderer;
    private String receiver;
    private String ordererContact;
    private String receiverContact;

    private String payment;                 //토스 결제 관련 정보
    private String tossOrderId;             //토스 결제 주문 번호
    private String tossMethod;              //토스 결제 방법
    private String tossBank;                //토스 결제 은행

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
