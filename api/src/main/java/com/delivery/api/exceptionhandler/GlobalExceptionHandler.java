package com.delivery.api.exceptionhandler;

import com.delivery.api.common.api.Api;
import com.delivery.api.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)   // 가장 마지막에 실행 적용 // default가 max_value이다 지금은 그냥 명시한거다
public class GlobalExceptionHandler {

    //모든 exception 은 다 캐치
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception (
            Exception exception
    ){
        log.error("",exception); //"스택을 찍는다"는 표현은 예외가 발생했을 때의 스택 트레이스(Stack Trace)를 콘솔이나 로그 파일에 기록한다는 뜻
        /*
        ex) 이게 스택 트레이스이다
        java.lang.NumberFormatException: For input string: "안녕하세요"
        at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67) ~[na:na]
        at java.base/java.lang.Integer.parseInt(Integer.java:668) ~[na:na]
        at java.base/java.lang.Integer.parseInt(Integer.java:786) ~[na:na]
        */

        return ResponseEntity
                .status(500) //서버에서 난 에러여서 그냥 500으로 처리
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR)
                );
    }
}