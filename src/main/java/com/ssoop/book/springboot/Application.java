package com.ssoop.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing // JPA Auditing 활성화
/*
스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정해줍니다.
@SpringBootApplication 이 있는 위치부터 설정을 읽어가기 때문에 프로젝트의 최상단에 위치해야합니다.
*/
@SpringBootApplication
public class Application { // 메인 클래스

    public static void main(String[] args) {

        // SpringApplication 으로 인해 내장 WAS를 실행합니다.
        // 서버에 톰캣을 설치할 필요가 없으며 Spring Boot로 만들어진 Jar 파일로 실행하면 됩니다.
        // '언제 어디서나 같은 환경에서 스프링 부트를 배포' 할 수 있기 때문에 내장 WAS를 사용하는 것을 권장하고 있습니다.
        SpringApplication.run(Application.class, args);
    }
}
