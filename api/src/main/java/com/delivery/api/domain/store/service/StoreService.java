package com.delivery.api.domain.store.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.store.StoreRepository;
import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long id){
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        //()->는 orElseThrow 메서드에 전달할 매개변수(람다 표현식)를 뜻합니다
        return entity.orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }


    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity){
        return Optional.ofNullable(storeEntity) //storeEntity가 null이 아닐 경우에 실행
                //map은 Optional 내부의 값이 null이 아닌 경우에만 실행되며, 값을 변환하거나 수정할 수 있도록 설계된 메서드
                .map(it ->{ //it는 storeEntity 객체 자체를 가리킵니다

                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    // TODO 등록일시 추가하기

                    return storeRepository.save(it);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }


    // 카테고리로 스토어 검색 = 카테고리 클릭시 리스트 출력
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory){
        var list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                storeCategory
        );

        return list;
    }


    // 전체 스토어
    public List<StoreEntity> registerStore(){
        var list = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return list;
    }

}