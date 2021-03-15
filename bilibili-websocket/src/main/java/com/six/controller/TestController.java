package com.six.controller;

import com.six.ws.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/websocket")
public class TestController {
    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/sendAllWebSocket")
    public String test(@RequestBody Map map) {
        webSocket.sendAllMessage((String) map.get("message"));
        return "websocket群体发送！";        
    }
    
    @RequestMapping("/sendOneWebSocket")
    public String sendOneWebSocket() {
        webSocket.sendOneMessage("DPS007", "只要你乖给你买条gai！");
        return "websocket单人发送";
    }
}