package com.ssoop.book.springboot.domain.posts;

import com.ssoop.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
// JPA의 어노테이션이며 테이블과 링크될 클래스임을 나타냅니다.
// 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다.
@Entity
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK 필드를 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타냅니다. IDENTITY 옵션을 추가해야 auto_increment가 됩니다.
    private Long id;

    // 테이블의 컬럼을 나타내며 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됩니다.
    // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;


    // Entity 클래스는 절대 Setter 메소드를 만들지 않습니다.
    // 생성자를 통하거나 Builder 패턴을 사용하여 값을 삽입해야합니다.
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야합니다.
    /*
    잘못된 예
    public class Order {
        private Boolean status;
        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    올바른 사용 예
    public class Order {
        private Boolean status;
        public void cancelOrder() {
            this.status = false;
        }
    }
     */
}
