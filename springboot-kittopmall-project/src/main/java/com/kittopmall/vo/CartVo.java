package com.kittopmall.vo;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Getter
@Builder
@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class CartVo {

    private Long id;
    private UserVo user;
    private ItemVo item;

}
