package com.delivery.api.common.error;

// 에러코드를 하나에다가 다 몰아서 쓰면 에러가 날 확률이 높아지기 때문에 따로 인터페이스를 만들어서 분리
public interface ErrorCodeIfs {

    Integer getHttpStatusCode();

    Integer getErrorCode();

    String getDescription();
}
