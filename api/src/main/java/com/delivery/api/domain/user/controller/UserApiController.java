package com.delivery.api.domain.user.controller;

import com.delivery.api.domain.user.business.UserBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController { // 이거는 권한이 없으면 접근 불가

    private final UserBusiness userBusiness;
}
