package com.delivery.api.account;

import com.delivery.api.account.model.AccountMeResponse;
import com.delivery.api.common.api.Api;
import com.delivery.api.common.error.UserErrorCode;
import com.delivery.db.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<Object> save() {
        var response = AccountMeResponse.builder()
                .name("홍길동")
                .email("V6xk5@example.com")
                .registeredAt(LocalDateTime.now())
                .build();


        return Api.ERROR(UserErrorCode.USER_NOT_FOUND,"홍길도 이라는 사용자 없음");
    }
}
