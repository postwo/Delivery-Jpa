package com.delivery.storeadmin.domain.sse.controller;

import com.delivery.storeadmin.domain.authorization.model.UserSession;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    // userConnection에다가 emitter를 저장
    private static final Map<String,SseEmitter> userConnection = new ConcurrentHashMap<>();

    // sse 연결
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        log.info("login user {}", userSession);

        // 클라이어트와 연결 = 객체를 생성하면서 연결
        var emitter = new SseEmitter(1000L*60);//ms 시간 단위로 재접속 시간을 조절 할 수 있다  //서버가 클라이언트에 실시간 데이터를 전송할 때 사용하는 객체
        userConnection.put(userSession.getUserId().toString(), emitter);

        emitter.onTimeout(()->{
            log.info("on timeout");
            // 클라이언트와 타이아웃이 일어났을때
            emitter.complete();// 연결을 종료하는 메서드 ,서버가 더 이상 클라이언트에 데이터를 보내지 않겠다는 신호
        });


        emitter.onCompletion(()-> {
            log.info("on completion");
            //클라이언트와 연결이 종료 됐을때 하는 작업
            userConnection.remove(userSession.getUserId().toString());
        });

        // 최초 연결시 응답 전송
        var event = SseEmitter
                .event()
                .name("onopen")
                ;

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }


    //메시지 보내기
    @GetMapping("/push-event")
    public void pushEvent(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        // 기존에 연결된 유저 찾기
        var emitter = userConnection.get(userSession.getUserId().toString());

        var event = SseEmitter
                .event()
                .data("hello") // onmessage에 저장된다
                ;

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}