package com.ssoop.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// MyBatis 에서 Dao라고 불리는 DB Layer 접근자로 생각하시면 됩니다.
// JPA에서는 Repository라고 불리며 인터페이스로 생성합니다.
// JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성됩니다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
