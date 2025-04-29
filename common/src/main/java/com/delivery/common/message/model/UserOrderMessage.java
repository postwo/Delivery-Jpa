package com.delivery.common.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 어노테이션이 안나온다 그럼 alt+ennter를 해서 Add lombok to class path를 클릭해주면 된다
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderMessage {

    private Long userOrderId;
}
