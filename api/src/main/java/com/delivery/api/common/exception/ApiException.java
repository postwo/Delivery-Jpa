package com.delivery.api.common.exception;


import com.delivery.api.common.error.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    //생성자 메소드
    public ApiException(ErrorCodeIfs errorCodeIfs) {
        // super를 통해 부모에게  그 값을 전달해서 세팅하고 ApiException을 호출하면 부모한테서 저장된값을 반환에서 받아와서 응답한다
        //super를 통해 부모 클래스(RuntimeException)에 값을 전달하면, 부모 클래스는 해당 값을 내부적으로 저장하고 관리합니다.
        // 그리고 ApiException을 호출하면, 부모 클래스에 저장된 값을 자동으로 반환하여 자식 클래스(ApiException)에서 활용할 수 있게 됩니다.
        super(errorCodeIfs.getDescription()); //RuntimeException(부모) 한테 내가 정의한 메시지를 보내버린다
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription){
        super(errorDescription); //RuntimeException(부모) 한테 내가 정의한 메시지를 보내버린다
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx){
        super(tx); //RuntimeException(부모) 한테 내가 정의한 메시지를 보내버린다
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx, String errorDescription){
        super(tx); //RuntimeException(부모) 한테 내가 정의한 메시지를 보내버린다
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
}
