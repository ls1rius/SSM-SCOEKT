package com.cm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@EnableWebSocket
public class MyWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyWebSocketHandler MyWebSocketHandler;

    private static final String LINK_URI = "/websocket";

    //添加websocket处理器，添加握手拦截器  拦截器先执行 然后到处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(MyWebSocketHandler, LINK_URI).addInterceptors(new MyHandShakeInterceptor());
    }

}