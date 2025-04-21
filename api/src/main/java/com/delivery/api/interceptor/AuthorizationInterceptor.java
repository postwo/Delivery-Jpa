package com.delivery.api.interceptor;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.TokenErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.business.TokenBusiness;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    //사전에 검증
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB ,chrome 의 경우 GET, POST OPTIONS = pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // js. html. png resource 를 요청하는 경우 = pass
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }


        // 토큰이 들어오면 authorization-token 에서 토큰을 꺼내온다
        var accessToken = request.getHeader("authorization-token"); // 헤더값에서 토큰을 빼온다
        if(accessToken == null){ // 토큰이 없으면 발생
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        // 토큰이 있으면 동작
        var userId = tokenBusiness.validationAccessToken(accessToken); // 여기서 값이 없으면 이미 tokenBusiness,service에서 에러 터졌기때문에 여기는 반드시 가져왔다고 생각하면 된다

        if(userId != null){
            //RequestContext 현재 요청에 대한 컨텍스트를 저장하고 관리하는 객체를 의미
            //RequestContext 한 가지 요청에대해서 글로벌하게 저장할수 있는  저장소에다가 저장
            //Objects.requireNonNull = 널 값 제거
            //RequestContextHolder.getRequestAttributes() == 현재 쓰레드의 요청과 연결된 RequestAttributes 객체를 반환
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

            // 여기마지막(RequestAttributes 이부분)에 넣을값이 어디다가 저장할건지 정하는거다 ex) 세션
            //equestAttributes.SCOPE_REQUEST = 리퀘스트 단위로 저장하겠다 = 이번 요청동안만 사용하겠다
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
            return true; //인증 성공
        }

        throw new ApiException(ErrorCode.BAD_REQUEST,"인증 실패");// 여기까지 오면 인증 실패
    }
}
