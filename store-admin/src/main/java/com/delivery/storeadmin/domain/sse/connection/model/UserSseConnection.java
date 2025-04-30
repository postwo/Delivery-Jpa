package com.delivery.storeadmin.domain.sse.connection.model;

import com.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {
    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ){
        // key 초기화
        this.uniqueKey = uniqueKey;

        // sse 초기화
        this.sseEmitter = new SseEmitter(60 * 1000L);

        // call back 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        // object mapper 초기화
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(()->{
            // connection pool remove
            /* 인자로 넘기는 this는 UserSseConnection 클래스의 객체(인스턴스)를 만들어서
            onCompletionCallback 메서드의 인자로 넘긴다는 의미이다*/
            this.connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(()->{
            this.sseEmitter.complete();
        });



        // onopen 메시지 =SSE 연결이 처음 열릴 때 보내는거
        this.sendMessage("onopen","connect");
    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ){
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json)
                    ;

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data){
        try {
            var json = this.objectMapper.writeValueAsString(data);

            var event = SseEmitter.event()
                    .data(json)
                    ;
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}