package ru.otus.lesson25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserRestController {

    @GetMapping("/login")
    String login() {
        return "login";
    }
}
