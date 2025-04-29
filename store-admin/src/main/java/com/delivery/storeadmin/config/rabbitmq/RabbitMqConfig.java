package com.delivery.storeadmin.config.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // 큐에서 데이터를 받아올 때 JSON을 객체로 변환해주는 역할만 필요

    // application.yml 에 있는 rabbitmq 설정을 가지고 커넥션 팩토리가 만들어진다

    // objectMapper를 매개변수로 받으면 자동으로 bean으로 등록된 objectmapper(=ObjectMapperConfig 커스텀한걸, 커스텀을 안했으면 기본적인 걸 가지고 온다)를 찾아온다
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
