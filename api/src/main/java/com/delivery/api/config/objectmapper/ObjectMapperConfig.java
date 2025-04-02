package com.delivery.api.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig { //전체 설정

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();

        // JDK 8에서 도입된 Optional 등과 같은 클래스를 직렬화/역직렬화할 수 있는 모듈을 등록.
        objectMapper.registerModule(new Jdk8Module());

        // Java 8 날짜 및 시간 API (e.g., LocalDate, LocalDateTime 등)의 직렬화/역직렬화를 처리하기 위한 모듈을 등록.
        objectMapper.registerModule(new JavaTimeModule());

        // JSON에서 알 수 없는 필드가 있을 경우 무시하도록 설정 (역직렬화 중 알 수 없는 필드로 인한 예외를 방지).
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 빈 클래스(필드가 없는 클래스)에 대한 직렬화를 허용 (예외 발생 방지).
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 날짜를 타임스탬프 형식(숫자) 대신 ISO-8601 문자열 형식으로 직렬화. //"2025-04-02T09:48:07.1292658" == iso 8601 이다
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // JSON 속성 이름을 스네이크 케이스(예: "user_name")로 변환하도록 설정.
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;
    }
}

