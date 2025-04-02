package com.delivery.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //Swagger와 관련된 설정을 담고 있으며, ModelResolver는 Swagger UI에서 API 문서화를 위해 사용
    //여기 ObjectMapper는 ObjectMapperConfig에 커스텀한 objectMapper를 가지고 와서 사용한다 스네이크케이스를 가진 objectmapper이다
    //snake케이스가 적용이 안돼서 커스텀한 objectmapper를 등록
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
