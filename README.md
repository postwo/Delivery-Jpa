# equals and hashCode = 객체비교 할때 사용 
equals와 hashCode를 구현하지 않으면, 객체는 기본적으로 메모리 주소를 기준으로 비교합니다.
즉, new 키워드를 사용해 객체를 생성하면 항상 새로운 메모리 주소가 할당되므로 equals를
사용했을 때도 false가 반환됩니다.하지만 @EqualsAndHashCode 또는 직접 equals와 hashCode를 구현하면,
객체의 필드 값을 기준으로 비교하게 됩니다. 그래서 주소가 달라도 필드 값이 같으면 동일한 객체로 간주되어 equals가 true를 반환

# Parameter 0 of constructor in com.delivery.api.account.AccountApiController required a bean of type 'com.delivery.db.account.AccountRepository' that could not be found.
스프링부트는 자신과 동일한 경로(패키지)에 있는 Bean들을 자신의 Bean으로 등록을 한다 com.delivery.api밑의 있는 bean,autowired,component,service,controller 등 어노테이션이
달려있는것들을 bean으로 등록하게 되어있다 근데 문제점은 com.delivery.db 라는 패키지명이 다르다 그러므로 db에서 bean으로 등록된 AccountRepository를 가져와서
사용할 수 없어서 위에러가 발생한거다

# 해결방안 
1.  com.delivery.db 를 똑같이 com.delivery.api라고 맞춰주면 해결할수 있다 
2.  com.delivery.db 에 하위에 있는 얘들도 bean으로 고려할수 있도록 등록해주는 방법이다 
= 이거는 가져다 사용할 패키지에서 config(여기서는 jpaconfig로 셍성)를 하나 생성하다 거기다가 scan을 작성하면 된다 