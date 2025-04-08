package com.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// enum클래스는 상속을 받을수없다 대신 구현은 가능하다
@AllArgsConstructor
@Getter //ErrorCodeIfs에 httpStatusCode, errorCode, description 에 것들을 구현해야하는데  getter를 통해 여기있는 값들을 return this.errorCode 이렇게 전달함으로 구현을 할 필요가 없다
public enum ErrorCode implements ErrorCodeIfs{

    //외부코등 ,내부코드(이거는 내가 서비스에 사용할 에러코드 이다,HttpStatus코드 와 일치 할 수도 있지만 일치 하지 않을수도 있다) ,그냥 내용
    OK(200, 200, "성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "null point"),


    ;

    // 변형이 안일어나게 하기 위해 final 붙임
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

    /*
    이 코드는 Enum 값(OK, BAD_REQUEST)에 포함된 데이터를 자동으로 해당 필드(httpStatusCode, errorCode, description)에 설정합니다.
    Lombok의 @AllArgsConstructor 덕분에 생성자를 따로 작성하지 않아도 각 Enum 값의 데이터가 필드에 세팅됩니다.

    코드 동작 과정
    Enum 값 정의:

    OK(200, 200, "성공") → HTTP 상태 코드가 200, 내부 에러 코드가 200, 설명이 "성공"으로 설정됩니다.

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청") → HTTP 상태 코드가 HttpStatus.BAD_REQUEST.value() (400), 내부 에러 코드가 400, 설명이 "잘못된 요청"으로 설정됩니다.

    생성자 호출:

    Lombok의 @AllArgsConstructor가 모든 필드를 포함하는 생성자를 자동 생성합니다.

    생성자는 Enum 값이 정의될 때 호출되어, 각 값(httpStatusCode, errorCode, description)을 설정합니다.

    ex)
    ErrorCode error = ErrorCode.OK;
    System.out.println(error.getHttpStatusCode()); // 200
    System.out.println(error.getDescription());   // 성공

    ErrorCode badRequestError = ErrorCode.BAD_REQUEST;
    System.out.println(badRequestError.getHttpStatusCode()); // 400
    System.out.println(badRequestError.getDescription());   // 잘못된 요청
    */

}
