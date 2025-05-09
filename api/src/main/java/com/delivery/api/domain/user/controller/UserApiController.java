package com.delivery.api.domain.user.controller;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.business.UserBusiness;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user") //로그인 된사용자
@RequiredArgsConstructor
public class UserApiController { // 이거는 권한이 없으면 접근 불가

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me( //@UserSession User 이렇게 작성하면 UserSessionResolver가 동작한다
            @UserSession User user
    ) {
        var response = userBusiness.me(user.getId());
        return Api.OK(response);
    }
}
