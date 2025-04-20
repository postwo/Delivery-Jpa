package com.delivery.api.domain.token.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {

    // 데이터 변환
    //DTO (Data Transfer Object) 형식인 TokenDto를 입력받아,
    //최종적으로 Response 객체인 TokenResponse를 반환합니다.
    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToken
    ){
        // 성공 토큰 에러
        Objects.requireNonNull(accessToken, ()->{throw new ApiException(ErrorCode.NULL_POINT);});
        // 재발급 토큰 에러
        Objects.requireNonNull(refreshToken, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
