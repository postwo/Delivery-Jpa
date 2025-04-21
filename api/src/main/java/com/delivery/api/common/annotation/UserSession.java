package com.delivery.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //메서드 매개변수에만 적용
@Retention(RetentionPolicy.RUNTIME) //런타임 시점까지 어노테이션이 유지됨
public @interface UserSession {
}
