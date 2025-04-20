package com.delivery.api.domain.token.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.ifs.TokenHelperIfs;
import com.delivery.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token 에 대한 도메인로직
 * // 토큰서비스는 userentity를 참고하면 안된다 자기네 토큰영역만 담당해야하기 때문에
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data); // 인터페이스 메서드 호출 토큰을 만들어서 리턴
        //issueAccessToken 이거를 호출함면 JwtTokenHelper에 있는 오버라이딩한게 동작해서 반환해준다
        // 자식 클래스인 JwtTokenHelper가 요청되면서 토큰을 생성
    }

    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data); // 토큰을 만들어서 리턴
    }

    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token); // 토큰 검증

        var userId = map.get("userId"); //토큰 값을 꺼내와서
        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString()); // 꺼내온값을 리턴
    }

}
