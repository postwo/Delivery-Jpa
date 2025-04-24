package com.delivery.api.domain.storemenu.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
            @Valid
            @RequestBody Api<StoreMenuRegisterRequest> request //Api<T>로 데이터를 감싸는 이유는 요청을 받을 때와 응답을 보낼 때 데이터 형식을 동일하게 유지하려는 목적 때문
    ){
        var req = request.getBody();
        var response = storeMenuBusiness.register(req);
        return Api.OK(response);
    }

}
