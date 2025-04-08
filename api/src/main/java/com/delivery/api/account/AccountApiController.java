package com.delivery.api.account;

import com.delivery.api.account.model.AccountMeResponse;
import com.delivery.api.common.api.Api;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
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
    public Api<AccountMeResponse> me() { // 이서비스는 항상성공만 존재 실패는 존재 하지 않는다라고 생각하기

        var response = AccountMeResponse.builder()
                .name("홍길동")
                .email("V6xk5@example.com")
                .registeredAt(LocalDateTime.now())
                .build();

        var str = "안녕하세요";
        var age =0;
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.SERVER_ERROR , e , "사용자 me 호출시 에러 발생");
        }



        return Api.OK(response);
    }
}
