package com.delivery.api.config.health;

import com.delivery.api.common.rabbitmq.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/open-api")
public class HealthOpenApiController {

    private final Producer producer;

    @GetMapping("/health")
    public void health(){
        log.info("health call");
        // producer에 다음 값들은 RabbitMqConfig에서 설정한 값들하고 일치해야 한다 directExchange,binding
        // object에는 보낼메시지를 작성하면 된다
        producer.producer("delivery.exchange", "delivery.key", "hello");
    }
}
