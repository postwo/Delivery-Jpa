package com.delivery.storeadmin.domain.userorder.business;

import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import com.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import com.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import com.delivery.storeadmin.domain.userorder.service.UserOrderService;
import com.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아서
     * push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문내역 없음"));

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity ->{ //userOrderMenuEntity =>userOrderMenuList의 각 요소를 의미하고
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                })
                .map(storeMenuEntity ->{ //storeMenuEntity =>storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()) 조회한 StoreMenu 정보를 뜻한다
                    return storeMenuConverter.toResponse(storeMenuEntity);
                })
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response
        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build()
                ;

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
        userConnection.sendMessage(push);

    }
}
