package com.mavic.storeapi.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hello")
    public Message Hello(){
        return new Message("Hello World");
    }
}
