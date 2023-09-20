package com.kittopmall.vo;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;
    private UserVo user;
    private ItemVo item;
    private OrderVo order;

    private String title;
    private String content;

    private LocalDateTime createdAt;



}
