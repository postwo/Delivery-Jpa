package com.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//API 요청 처리 후 데이터를 클라이언트에게 반환할 때 사용하는 응답 객체를 정의한 클래스입니다. 요청 결과를 Result와 body로 구성된 형태로 반환
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    /* ex) 
    {
      "result": {
        "result_code": 200,
        "result_message": "ok",
        "result_description": "성공"
      },
      "body": {
        "email": "V6xk5@example.com",
        "name": "홍길동",
        "registered_at": "2025-04-02T23:06:28.9511347"
      }
    }
    */


    //Result: 성공/실패 상태 및 관련 정보
    private Result result;

    //응답할 데이터를 뜻한다
    @Valid
    private T body;


    //이거의 타입 Api<T> 이거인 이유는 data가 T 타입이여서 그런다
    public static <T> Api<T> OK(T data){
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

}
