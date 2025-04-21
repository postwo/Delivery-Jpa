package com.delivery.api.domain.user.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.UserErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.user.UserEntity;
import com.delivery.db.user.UserRepository;
import com.delivery.db.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/*
* user 도메인 롲기을 처리 하는 서비스*/

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it ->{
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }

    //로그인
    public UserEntity login(String email ,String password){
      var entity = getUserWithThrow(email, password);
      return entity;
    }

    //등록된 사용자가 있는지 체크
    public UserEntity getUserWithThrow(
            String email
            ,String password
    ){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc
                (email, password, UserStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    //로그인한 사용자 정보 조회
    public UserEntity getUserWithThrow(
           Long userId
    ){
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                        userId, UserStatus.REGISTERED)
                .orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
