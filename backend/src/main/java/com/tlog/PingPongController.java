package com.tlog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong(String result){}

    @GetMapping("/ping")
    public PingPong getPingPong(){
        return new PingPong("Pong");
    }
    @GetMapping("/pong")
    public PingPong getPongPing(){
        return new PingPong("* Pong Ping");
    }


}
