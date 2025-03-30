package com.delivery.db.account;


import com.delivery.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder //그냥 builder를 달면 부모가 가지고 있는 필드값이 안뜬다 그러므로 super를 달아야 한다  // 부모로부터 상속 받은 변수도 포함시킨다
@Data
@EqualsAndHashCode(callSuper = true) //부모 클래스의 필드도 equals와 hashCode 메서드에 포함하도록 설정
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {


}
