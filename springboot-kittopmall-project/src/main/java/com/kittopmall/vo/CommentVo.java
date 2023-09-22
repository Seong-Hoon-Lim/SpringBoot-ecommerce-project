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
    private String content;
    private LocalDateTime createdAt;
    private QuestionVo question;



}
