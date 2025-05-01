package com.delivery.storeadmin.domain.userorder.consumer;

import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.storeadmin.domain.userorder.business.UserOrderBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrderConsumer {

    private final UserOrderBusiness userOrderBusiness;

    // RabbitListener = 어떤 큐로부터 데이터를 받아올건지 정한다
    // userOrderMessage 객체는 큐에서 가져온 데이터를 담는 역할을 한다
    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(
            UserOrderMessage userOrderMessage
    ){
        log.info("message queue >> {}", userOrderMessage);
        userOrderBusiness.pushUserOrder(userOrderMessage);
    }
}