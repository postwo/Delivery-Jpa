package com.delivery.storeadmin.domain.user.controller;

import com.delivery.storeadmin.domain.user.business.StoreUserBusiness;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    // API<StoreUserResponse> 이렇게 감싸지 않은 이유는 api 모듈 이모듈이랑 다르기 때문에 똑같이 사용할수 없다 사용할려면 이 모듈에서 다시 설정하면 된다
    // API 설정을 가져다 사용할수도 있긴 한데 그런데 이렇게 하면 API에 불필요한 패키지설정들도 가져다 사용하기 때문에 이렇게 사용하는건 추천하지 않는다
    @PostMapping("")
    public StoreUserResponse register(
            @Valid
            @RequestBody StoreUserRegisterRequest request
    ){
        var response = storeUserBusiness.register(request);
        return response;
    }
}