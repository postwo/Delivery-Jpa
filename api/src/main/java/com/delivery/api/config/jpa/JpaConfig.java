package com.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.delivery.db")
//basePackages는 entity가 있는곳에 어떤 패키지가 있냐라는 뜻이다
// com.delivery.db로 가면 찾고자 하는 entity가 있다
// db패키지 하위에 있는 entity어노테이션이 붙은 것들을 다 스캔하겠다는 의미다
@EnableJpaRepositories(basePackages = "com.delivery.db")
// db패키지 하위에 있는 repository어노테이션이 붙은 것들을 다 스캔하겠다는 의미다
public class JpaConfig {
}
