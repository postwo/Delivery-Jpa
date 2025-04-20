package com.delivery.api.domain.user.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.token.business.TokenBusiness;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.user.controller.model.UserLoginRequest;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.converter.UserConverter;
import com.delivery.api.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness { //흐름  controller -> business -> service -> repository

    private final UserService userService;
    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    //회원 가입
    /*
    사용자에 대한 가입처리 로직
    1. request -> entity
    2. entity -> save
    3. save Entity -> response
    4. response return
    */
    public UserResponse register(UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

        //이거는 함수형방식
        /*return Optional.ofNullable(request)
            .map(userConverter::toEntity)
            .map(userService::register)
            .map(userConverter::toResponse)
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));*/
    }

    //로그인
    /**
     * 1. email, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public TokenResponse login(@Valid UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }
}
