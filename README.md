# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스 / 이동욱

스프링 부트와 AWS로 혼자 구현하는 웹 서비스 책 내용으로 실습한 저장소입니다.  
책을 구매하여 직접 실습해보시는 것을 추천드립니다.

## 프로젝트 환경
**현재 최신버전으로 올라가면서 책 예제로는 동작하지 않는 코드들이 발생합니다.**  
**아래에 있는 환경으로 진행할 예정입니다. 책과 다소 다른 점이 있습니다.**

- Java 8
- Gradle 6.8
- Spring Boot 2.5.4

## 1장 인텔리제이로 스프링 부트 시작하기

### 이클립스에 비해 인텔리제이가 갖는 강점

- 강력한 추천 기능(Smart Completion)
- 훨씬 더 다양한 리팩토링과 디버깅 기능
- 이클립스의 깃(Git)에 비해 훨씬 높은 자유도
- 프로젝트 시작할 때 인덱싱을 하여 파일을 비롯한 자원들에 대한 빠른 검색 속도
- HTML과 CSS, JS, XML에 대한 강력한 기능 지원
- 자바, 스프링 부트 버전업에 맞춘 빠른 업데이트

### Gradle 설정
```groovy
// 플러그인 의존성 적용
plugins {
    id 'org.springframework.boot' version '2.5.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE' // 스프링 부트의 의존성을 관리해주는 플러그인
    id 'java'
}

group = 'com.ssoop.book'
version = '1.0.0-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
    jcenter()
}

// 프로젝트 개발에 필요한 의존성들을 선언하는 곳
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
    useJUnitPlatform()
}
```

## 2장 스프링 부트에서 테스트 코드를 작성하자

### 테스트 코드

TDD와 단위테스트는 다른 이야기입니다.

TDD는 **테스트가 주도하는 개발**을 이야기합니다.  
테스트 코드를 먼저 작성하는 것부터 시작합니다.

단위 테스트는 **기능 단위의 테스트를 코드를 작성**하는 것이며 TDD와 달리 테스트 코드를 꼭 먼저 작성해야 하는 것도 아니고,   
리팩토링도 포함되지 않습니다.
순수하게 테스트코드만 작성하는 것입니다.

TDD에 더 알아보고 싶다면?  
[TDD 실천법과 도구](https://repo.yona.io/doortts/blog/issue/1)


### 단위 테스트 코드 이점
- 단위 테스트는 개발단계 초기에 문제를 발견하게 도와줍니다.
- 단위 테스트는 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업데이트 등에서 기존 기능이 올바르게 작동하는지 확인할 수 있습니다. (예: 회귀테스트)
- 단위 테스트는 기능에 대한 불확실성을 감소시킬 수 있습니다.
- 단위 테스트는 시스템에 대한 실제 문서를 제공합니다. 즉, 단위 테스트 자체가 문서로 사용할 수 있습니다.

#### 경험담
- 빠른 피드백 (수동 검증이 아닌 자동검증)
- 개발자가 만든 기능을 안전하게 보호 (새로운 기능 추가시 빠르게 문제점을 파악할 수 있음)


### Spring Boot 설명

**@SpringBootApplication**
스프링 부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정됩니다.  
`@SpringBootApplication`이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치해야합니다.

**내장 WAS**
SpringApplication.run으로 인해 내장 WAS(Web Application Server, 웹 애플리케이션 서버)를 실행합니다.
별도로 외부에 WAS를 두지 않고 애플리케이션을 실행 할 때 내부에서 WAS를 실행하는 것을 이야기합니다.
톰캣을 설치할 필요가 없고 스프링 부트로 만들어진 Jar 파일(실행 가능한 Java 패키징 파일)로 실행하면 됩니다.

'언제 어디서나 같은 환경에서 스프링 부트를 배포' 할 수 있기 때문에 내장 WAS 사용을 권장하고 있습니다.

**@RestController**
- 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줍니다.
- 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다고 생각하면 됩니다.

**@GetMapping**
- HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 줍니다.
- 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용되었습니다.

**@RunWith(SpringRunner.class)**
- 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킵니다.
- 여기서는 SpringRunner라는 스프링 실행자를 사용합니다.
- 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 합니다.

**@WebMvcTest**
- 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션입니다.
- 선언을 할경우 @Controller, @ControllerAdvice 등을 사용할 수 있습니다.
- 단, @Service, @Component, @Repository 등은 사용할 수 없습니다.

**@Autowired**
- 스프링이 관리하는 빈(Bean)을 주입 받습니다.

**MockMvc**
- 웹 API를 테스트할 때 사용합니다.
- 스프링 MVC 테스트의 시작점입니다.
- 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있습니다.

**mvc.perform(get("hello"))**
- MockMvc를 통해 /hello 주소로 HTTP GET 요청을 합니다.
- 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있습니다.

**.andExpect(status().isOk())**
- mvc.perform의 결과를 검증합니다.
- HTTP Header의 Status를 검증합니다.
- 200, 404, 500 등의 상태를 검증합니다.

**.andExpect(content().string(hello))**
- mvc.perform의 결과를 검증합니다.
- 응답 본문의 내용을 검증합니다.

**assertThat**
- assertj라는 테스트 검증 라이브러리의 검증 메소드입니다.
- 검증하고 싶은 대상을 메소드 인자로 받습니다.
- 메소드 체이닝이 지원되어 isEqualTo와 같이 메소드를 이어서 사용이 가능합니다.

**isEqualTo**
- assertj의 동등 비교 메소드입니다.
- assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공입니다.

**@RequestParam**
- 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션입니다.

**param**
- API 테스트할 때 사용될 요청 파라미터를 설정합니다.
- 단, 값은 String만 허용합니다.
- 그래서 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야합니다.

**jsonPath**
- JSON 응답값을 필드별로 검증할 수 있는 메소드입니다.
- $를 기준으로 필드명을 명시합니다.

## 스프링 부트에서 JPA로 데이터베이스 다뤄보자





