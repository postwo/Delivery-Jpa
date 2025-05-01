package com.delivery.api.domain.userorder.coontroller.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    @NotNull
    private Long storeId;

    // 주문
    // 특정 사용자가 , 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자
    // 특정 메뉴 id
    // ex) 아메리카노+ 카레라이스+ 볶음밥 이런형식으로 여러개의 주문을 받기 떄문에 list사용
    @NotNull
    private List<Long> storeMenuIdList;

}
