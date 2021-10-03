package com.ssoop.book.springboot.web;

import com.ssoop.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
*  JSON을 반환하는 컨트롤러로 만들어 줍니다.
*  @ResponseBody를 각 메소드마다 선언한 것을 생략하고 한 번에 사용할 수 있게 해줍니다.
* */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}
