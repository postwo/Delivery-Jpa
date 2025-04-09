package com.delivery.db.user;

import com.delivery.db.BaseEntity;
import com.delivery.db.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
//@Entity(name = "user") 이렇게 작성하나 @Table(name = "user") 이렇게 하나 기능은 똑같다
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)//부모 클래스의 필드도 equals와 hashCode 메서드에 포함하도록 설정
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder//그냥 builder를 달면 부모가 가지고 있는 필드값이 안뜬다 그러므로 super를 달아야 한다  // 부모로부터 상속 받은 변수도 포함시킨다
public class UserEntity extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(length = 150, nullable = false)
    private String address;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;

}
