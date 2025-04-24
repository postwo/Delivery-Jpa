package com.delivery.api.domain.storemenu.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.storemenu.StoreMenuRepository;
import com.delivery.db.storemenu.enums.StoreMenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    //메뉴를 가지고 온다
    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED);
        return entity.orElseThrow(()->new ApiException(ErrorCode.NULL_POINT)); //() ->는 Supplier 인터페이스의 get() 메서드를 구현하는 람다 표현식
    }

    // storeid를 가지고 온다
    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }

    // 메뉴 등록
    public StoreMenuEntity register(
            StoreMenuEntity storeMenuEntity
    ){
        return Optional.ofNullable(storeMenuEntity)
                .map(it ->{
                    it.setStatus(StoreMenuStatus.REGISTERED);
                    return storeMenuRepository.save(it);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));

    }

}
