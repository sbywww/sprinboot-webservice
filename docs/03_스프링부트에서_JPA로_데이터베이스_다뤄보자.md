# 3장 스프링부트에서 JPA로 데이터베이스 다뤄보자

## JPA 소개

JPA 소개를 하기 전,  
MyBatis와 iBatis에 대해 집고 넘어가야 합니다.  
MyBatis, iBatis는 ORM이 아니고 SQL Mapper입니다.  
가끔 ORM에 대해 MyBatis, iBatis를 얘기하게 되는데 이 둘은 ORM이 아닙니다.  
ORM은 객체를 매핑하는 것이고, SQL Mapper는 쿼리를 매핑합니다.

JPA는 객체지향 프로그래밍 언어와 관계형 데이터베이스를 중간에서 패러다임 일치를 시켜주기 위한 기술입니다.  
개발자는 객체지향적으로 프로그래밍을 하고, JPA가 이를 관계형 데이터베이스에 맞게 SQL을 대신 생성해서 실행합니다.  

- 항상 객체 지향적으로 코드를 표현할 수 있으니 더는 SQL에 종속적인 개발을 하지 않아도 됩니다.  
- 객체 중심으로 개발을 하게 되므로 생산성 향상
- 유지보수가 편해집니다.

이런 점 때문에 규모가 크고 365일 24시간, 대규모 트래픽과 데이터를 가진 서비스에서 JPA는 표준 기술로 자리 잡고 있습니다.

## Spring Data JPA

JPA는 인터페이스로서 자바 표준명세서 입니다.

인터페이스인 JPA를 사용하기 위해서는 구현체가 필요합니다. 대표적으로 Hibernate, Eclipse Link 등이 있습니다.  
하지만 Spring에서 JPA를 사용할 때는 이 구현체들을 직접 다루지 않습니다.

구현체들을 좀 더 쉽게 사용하고자 추상화시킨 Spring Data JPA라는 모듈을 이용하여 JPA 기술을 다룹니다.
이들의 관계를 보면 다음과 같습니다.

`JPA <- Hibernate <- Spring Data JPA`

Hibernate를 쓰는 것과 Spring Data JPA를 쓰는 것 사이에는 큰 차이가 없습니다.
그럼에도 Spring Data JPA를 권장하고 있습니다.

- 구현체 교체의 용이성
- 저장소 교체의 용이성

### 구현체 교체의 용이성

Hibernate 외에 다른 구현체로 쉽게 교체하기 위함입니다.
Hibernate가 언젠간 수명을 다해 새로운 JPA 구현체가 대세로 떠오르고, 
Spring Data JPA를 쓰는 중이라면 아주 쉽게 교체할 수 있습니다.  
Spring Data JPA 내부에서 구현체 매핑을 지원해주기 때문입니다.

실제 예를 들어 Redis 클라이언트가 Jedis에서 Lettuce로 대세가 넘어갈 때,
Spring Data Redis를 쓰신 분들은 아주 쉽게 교체를 했습니다.

### 저장소 교체의 용이성

관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함입니다.

예를 들어, 서비스 초기에는 관계형 데이터베이스로 모든 기능을 처리했지만, 
점점 트래픽이 많아져 관계형 데이터베이스로는 도저히 감당이 안 될 때가 있을 수 있습니다.
이 때 MongoDB로 교체가 필요하다면 Spring Data JPA에서 Spring Data MongoDB로 의존성만 교체하면 됩니다.

Spring Data의 하위 프로젝트들은 기본적인 CRUD의 인터페이스가 같기 때문에 저장소가 변경되어도
기본적인 기능은 변경할 것이 없습니다. Spring Data JPA, Spring Data Redis, Spring Data MongoDB 등등
Spring Data의 하위 프로젝트들은 save(), findAll(), findOne() 등을 인터페이스로 갖고 있습니다.

## 실무에서의 JPA

JPA를 잘 쓰려면 객체지향 프로그래밍과 관계형 데이터베이스를 둘 다 이해해야 합니다.  
CRUD 쿼리를 직접 작성할 필요가 없으며, 
부모-자식 관계 표현, 1:N 관계 표현, 
상태와 행위를 한 곳에서 관리하는 등 객체지향 프로그래밍을 쉽게 할 수 있습니다.

JPA에서는 여러 성능 이슈 해결책들을 이미 준비해놓은 상태이기 때문에 잘 활용하면 네이티브 쿼리만큼의 퍼포먼스를 낼 수 있습니다.


## Spring Data JPA 적용

### spring-boot-stater-data-jpa
- 스프링 부트용 Spring Data Jpa 추상화 라이브러리입니다.
- 스프링 부트 버전에 맞춰 자동으로 JPA관련 라이브러리들이 버전을 관리해줍니다.

### h2
- 인메모리 관계형 데이터베이스입니다.
- 별도의 설치가 필요 없이 프로젝트 의존성만으로 관리할 수 있습니다.
- 메모리에서 실행되기 때문에 애플리케이션을 재시작할 때마다 초기화된다는 점을 이용하여 테스트 용도로 많이 사용됩니다.

### domain

도메인이란, 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역

도메인을 좀 더 자세히 알고 싶다면? [DDD Starter](http://www.yes24.com/Product/Goods/27750871)
를 참고하시면 됩니다.

### Entity
- 테이블과 링크될 클래스입니다.
- 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다.
- ex) SalesManager.java -> sales_manager table

### @Id
- 해당 테이블의 PK 필드를 나타냅니다.

### @GeneratedValue
- PK의 생성 규칙을 나타냅니다.
- 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됩니다.
- PK는 Long 타입의 auto_increment를 추천합니다.(MySQL 기준으로 bigint 타입)
    - FK를 맺을 때 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야하는 상황이 발생합니다.
    - 인덱스에 좋은 영향을 끼치지 못합니다.
    - 유니크한 조건이 변경 될 경우 PK 전체를 수정해야 하는 일이 발생합니다.

### @Column
- 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됩니다.
- 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
- 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 
  타입을 TEXT로 변경하고 싶거나 등에 사용됩니다.
  
### Setter

Entity 클래스에서는 절대 Setter 메소드를 만들지 않습니다.  
대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야합니다.

잘못된 사용 예)
```java
public class Order {
    public void setStatus(boolean status) {
        this.status = status;
    }
}

public void 주문서비스의_취소이벤트 () {
    order.setStatus(false);
}
```

올바른 사용 예)
```java
public class Order {
    public void cancelOrder() {
        this.status = false;
    }
}

public void 주문서비스의_취소이벤트() {
    order.cancelOrder();
}
```

Setter가 없는 상황에서 값을 채워넣는 방법으로는 **생성자를 통해** 채워야 하며,
값 변경이 필요한 경우 **해당 이벤트에 맞는 public 메소드를 호출**하여 변경하는 것을 전제로 합니다.

### JpaRepository

MyBatis, iBatis 등에서 Dao라고 불리는 DB Layer 접근자 입니다.
JPA에선 Repository라고 부르며 인터페이스로 생성합니다.  
**JpaRepository<Entity 클래스, PK 타입>를 상속**하면 기본적인 CRUD 메소드가 자동으로 생성됩니다.

@Repository를 추가할 필요가 없으며, Entity 클래스와 기본 Entity Repository는 함께 위치해야합니다.
이 둘은 매우 밀접한 관계이며, Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수가 없습니다.

프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면, Entity 클래스와 기본 Repository는 함께 움직여야 하므로
**도메인 패키지에서 함께 관리**합니다.

### @After
- Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
- 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용합니다.
- 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 실패할 수 있습니다.

### Repository.save
- 테이블에 insert/update 쿼리를 실행합니다.
- id 값이 있다면 update, 없다면 insert 쿼리가 실행됩니다.

### Repository.findAll
- 테이블에 있는 모든 데이터를 조회해오는 메소드입니다.


### SpringBootTest
- SpringBootTest를 사용할 경우 H2 데이터베이스를 자동으로 실행해줍니다.

### Jpa로 실행된 쿼리를 보는 옵션
```
spring.jpa.show_sql=true
```

### Jpa를 MySQL 버전으로 변경하는 옵션
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.datasource.hikari.jdbc-url=jdbc:h2:mem://localhost/~/testdb;MODE=MYSQL
```

### 비지니스 로직 처리
Service에서 비즈니스 로직을 처리해야한다고 알고 있지만,  
Service는 트랜잭션, 도메인 간 순서 보장의 역할만 합니다.  

![](./images/1.png)

- Web Layer
  - 흔히 사용하는 컨트롤러(@Controller)와 JSP/Freemarker 등의 뷰 템플릿 영역입니다.
  - 이외에도 필터(@Filter), 인터셉터, 컨트롤러 어드바이스(@ControllerAdvice) 등 외부 요청과 응답에 대한 전반적인 영역을 이야기 합니다.

- Service Layer
  - @Service에 사용되는 서비스 영역입니다.
  - 일반적으로 Controller와 Dao의 중간 영역에서 사용됩니다.
  - @Transaction이 사용되어야 하는 영역이기도 합니다.
  
- Repository Layer
  - Database와 같이 데이터 저장소에 접근하는 영역입니다.
  - Dao(Data Access Object) 영역으로 불리는 영역입니다.
  
- Dtos
  - Dto(Data Transfer Object)는 계층 간에 데이터 교환을 위한 객체를 이야기하며 Dtos는 이들의 영역을 얘기합니다.
  - 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등이 이들을 이야기합니다.
  
- Domain Model
  - 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화 시킨 것을 도메인 모델이라고 합니다.
  - 이를테면 택시 앱이라고하면 배차, 탑승, 요금 등이 모두 도메인이 될 수 있습니다.
  - @Entity를 사용했으면 사용된 영역 역시 도메인 모델이라고 할 수 있습니다.
  - 무조건 데이터베이스의 테이블과 관계가 있어야만 하는 것은 아닙니다.
  - VO처럼 값 객체들도 이 영역에 해당하기 때문입니다.
  
기존에 서비스로 처리하던 방식을 **트랜잭션 스크립트**라고 합니다.

슈도 코드)
```java
@Transactional
public Order cancelOrder(int orderId) {
    1) 데이터 베이스로부터 주문정보(Orders), 결제정보(Billing), 배송정보(Delivery) 조회
    2) 배송 취소를 해야하는 지 확인
    3) if (배송 중이라면) {
        배송 취소로 변경
    }
    4) 각 테이블에 취소 상태 Update
}
```

실제 코드)
```java
@Transactional
public Order cancelOrder(int orderId) {
    // 1)
    OrdersDto order = ordersDao.selectOrders(orderId);
    BillingDto billing = billingDao.selectBilling(orderId);
    DeliveryDto delivery = deliveryDao.selectDelivery(orderId);
    
    // 2)
    String deliveryStatus = delivery.getStatus();
    
    // 3)
    if("IN_PROGRESS".equals(deliveryStatus)) {
        delivery.setStatus("CANCEL");
        deliveryDao.update(delivery);
    }
    
    // 4)
    order.setStatus("CANCEL");
    ordersDao.update(order);
    
    billing.setStatus("CANCEL");
    billingDao.update(billing);
    
    return order;
}
```

모든 로직이 서비스 클래스 내부에서 처리됩니다. 그러다보니 서비스 계층이 무의미하며,  
객체란 단순히 데이터 덩어리 역할만 하게 됩니다.

반면, 도메인 모델에서 처리할 경우는 다음과 같습니다.
```java
@Transactional
public Order cancelOrder(int orderId) {
    // 1)
    OrdersDto order = ordersRepository.findById(orderId);
    BillingDto billing = billingRepository.findByOrderId(orderId);
    DeliveryDto delivery = deliveryRepository.findByOrderId(orderId);
    
    // 2-3)
    delivery.cancel();
    
    // 4)
    order.cancel();
    billing.cancel();
    
    return order;
}
```
order, billing, delivery가 각자 본인의 취소 이벤트 처리를 하며,  
서비스 메소드는 **트랜잭션과 도메인 간의 순서만 보장**해 줍니다.

### 스프링에서 Bean 주입 방식

- @Autowired
- setter
- 생성자

이 중 가장 권장하는 방식은 **생성자로 주입**받는 방식입니다. (@Autowired는 권장하지 않습니다.)  
생성자로 Bean 객체를 주입 받도록 하면 @Autowired와 동일한 효과를 볼 수 있습니다.

### Dto 클래스를 생성한 이유
Entity 클래스와 거의 유사한 형태이지만 Dto 클래스를 추가 생성했습니다.  
하지만, **절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안됩니다.**  
Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스입니다.  
Entity 클래스를 기준으로 테이블이 생성되고, 스키마가 변경됩니다.   
화면 변경은 아주 사소한 기능 변경인데, 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경입니다.
수많은 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작합니다.  
Entity 클래스가 변경되면 여러 클래스에 영향을 끼치지만 Request와 Response용 Dto는 View를 위한 클래스라
정말 자주 변경이 필요합니다.

View Layer와 DB Layer의 역할 분리를 철저하게 하는 게 좋습니다.
실제로 Controller에서 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 
Entity 클래스만으로 표현하기가 어려운 경우가 많습니다.

꼭 Entity 클래스와 Controller에서 쓸 Dto는 분리해서 사용해야 합니다.

### JPA 쿼리를 사용하지 않고 저장되는 이유
update 기능에서 데이터베이스에서 **쿼리를 날리는 부분이 없습니다.**
그 이유는 JPA의 **영속성 컨텍스트** 때문입니다.

영속성 컨텍스트란, **엔티티를 영구 저장하는 환경**입니다. 일종의 논리적 개념이며, JPA의 핵심 내용은
**엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐**로 갈립니다.

JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 사용하면 기본 옵션) **트랜잭션 안에서
데이터베이스에서 데이터를 가져오면** 이 데이터는 영속성 컨텍스트가 유지된 상태입니다.

이 상태에서 해당 데이터의 값을 변경하면 **트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영**합니다.
즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다 라는 것입니다.
이 개념을 **더티체킹**이라고 합니다.

[더티 체킹(Dirty Checking)이란?](https://jojoldu.tistory.com/415)

### JPA Auditing으로 생성시간/수정시간 자동화하기
보통 엔티티에서는 해당 데이터의 생성시간과 수정시간을 포함합니다.  
언제 만들어졌는지, 언제 수정되었는지 등은 차후 유지보수에 있어서 굉장히 중요한 정보입니다.  
그렇다 보니 매번 DB에 삽입하기 전, 갱신 하기 전에 날짜 데이터를 등록/수정하는 코드가 여기저기 들어갑니다.  
단순하고 반복적인 코드가 모든 테이블과 서비스 메소드에 포함되어야 한다고 생각하면 귀찮고 코드가 지저분해집니다.  
이런 이유로 JPA Auditing을 사용합니다.

### LocalDate 사용
Java8부터 LocalDate와 LocalDateTime이 등장했습니다. 
그간 Java의 기본 날짜 타입인 Date의 문제점을 고친 타입이라 Java8일 경우 무조건 써야한다고 생각하면 됩니다.

#### 참고
Java8이 나오기 전까지 사용되었던 Date와 Calendar 클래스는 다음과 같은 문제들이 있습니다.

1. 불변(변경이 불가능한) 객체가 아닙니다.
    - 멀티스레드 환경에서 언제든 문제가 발생할 수 있습니다.

2. Calendar는 월(Month) 값 설계가 잘못되었습니다.
    - 10월을 나타내는 Calendar.OCTOBER의 숫자 값은 '9'입니다.
    - 당연히 '10'으로 생각했던 개발자들에게는 큰 혼란이 왔습니다.

JodaTime이라는 오픈소스를 사용해서 문제점들을 피했었고, Java8에선 LocalDate를 통해 해결했습니다.  
자세한 내용은 Naver D2 - [Java의 날짜와 시간 API](https://d2.naver.com/helloworld/645609) 를 참고하시면 됩니다.

### @MappedSuperclass
- JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 합니다.

### @EntityListeners(AuditingEntityListener.class)
- BaseTimeEntity 클래스에 Auditing 기능을 포함 시킵니다.

### @CreatedDate
- Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.

### @LastModifiedDate
- 조회한 Entity의 값을 변경할 때 시간이 자동 저장됩니다.












