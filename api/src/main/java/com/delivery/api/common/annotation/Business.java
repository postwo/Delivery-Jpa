package com.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

//어노테이션으로 적용시키기위해 target 사용
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service // 서비스말고 component라고 작성해도 상관없다
public @interface Business {

    @AliasFor(annotation = Service.class)
    String value() default "";
}
