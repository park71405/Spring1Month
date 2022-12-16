package kr.or.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.dto.UserDto;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, @RequestParam String name) {
        System.out.println("--------------- @GetMapping ---------------");
//        System.out.println("get method");
//        System.out.println("id: " + id);
//        System.out.println("name: " + name);
        return id + " " + name;
    }

    @PostMapping
    public UserDto post(@RequestBody UserDto userDto) {
        System.out.println("--------------- @PostMapping ---------------");
//        System.out.println("post method");
//        System.out.println("user: " + user);
        //나는 park11111
        //나는 park22222
        return userDto;
    }
}
