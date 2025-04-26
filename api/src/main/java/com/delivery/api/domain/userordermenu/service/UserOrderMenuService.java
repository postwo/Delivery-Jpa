package com.delivery.api.domain.userordermenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.db.userordermenu.UserOrderMenuRepository;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    //주문 목록
    //userOrderId = 주문 id
    //결과가 없으면 빈 리스트를 반환합니다. 이를 통해 null이 아니라 빈 리스트이므로 별도의 null 체크가 필요하지 않습니다
    //List 타입은 결과가 없을 때 null이 아니라 빈 리스트를 반환합니다.
    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId){
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }


    public UserOrderMenuEntity order(
            UserOrderMenuEntity userOrderMenuEntity
    ){
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it ->{
                    it.setStatus(UserOrderMenuStatus.REGISTERED);
                    return userOrderMenuRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

}