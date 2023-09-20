package com.kittopmall.vo;

import com.kittopmall.vo.constants.Category;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {

    private Long id;
    private Category category;
    private String itemName;
    private Double price;
    private String content;
    private Integer stock;
    private Integer hit;

    private String imgName;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;


}
