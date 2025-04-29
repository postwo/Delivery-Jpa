package com.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;

@Configuration
public class RabbitMqConfig {

    @Bean
    public DirectExchange directExchange(){ // producer 가보낸 데이터를 여기다가 보낸다
        return new DirectExchange("delivery.exchange");
    }

    @Bean
    public Queue queue(){ // 여기에다가 데이터를 담아줄 큐를 생성
        return new Queue("delivery.queue");
    }

    //Binding 설정을 통해 Exchange와 Queue를 연결해주는 역할
    //이를 통해 특정 라우팅 키로 메시지가 큐에 전달될 수 있게 설정
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }


    /// end queue 설정

    //RabbitTemplate = objectmapper 같은 기능을한다
    //RabbitTemplate => 오브젝트를 받으면 json으로 변경,json으로 받으면 오브젝트 형태로 변경
    //ConnectionFactory는 yml에 설정한 rabbitmq 정보(host, port, username, password)를 기반으로 Spring이 자동으로 생성해 주입
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ){
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // objectMapper를 매개변수로 받으면 자동으로 bean으로 등록된 objectmapper(=ObjectMapperConfig 커스텀한걸, 커스텀을 안했으면 기보적인 걸 가지고 온다)를 찾아온다
    //MessageConverter 메서드를 rabbitTemplate메서드에 매개변수로 넘겨진다
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
