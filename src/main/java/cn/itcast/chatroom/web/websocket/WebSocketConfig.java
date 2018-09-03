package cn.itcast.chatroom.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 说明：WebScoket配置处理器
 * 把处理器和拦截器注册到spring websocket中
 */
@Component("webSocketConfig")
//配置开启WebSocket服务用来接收ws请求
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    //注入处理器
    @Autowired
    private ChatWebSocketHandler webSocketHandler;
    //注入拦截器
    @Autowired
    private ChatHandshakeInterceptor chatHandshakeInterceptor;

    //注册具体的服务
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //添加一个处理器还有定义处理器的处理路径
        //参数1：websocket的处理器，用来处理消息
        //参数2：websocket的访问地址，"ws://" + path + "ws"
        registry.addHandler(webSocketHandler, "/ws").addInterceptors(chatHandshakeInterceptor);
        /*
         * 在这里我们用到.withSockJS()，SockJS是spring用来处理浏览器对websocket的兼容性，
         * 目前浏览器支持websocket还不是很好，特别是IE10以下.
         * SockJS能根据浏览器能否支持websocket来提供三种方式用于websocket请求，
         * 三种方式分别是 WebSocket, HTTP Streaming以及 HTTP Long Polling
         */
        registry.addHandler(webSocketHandler, "/ws/sockjs").addInterceptors(chatHandshakeInterceptor).withSockJS();
    }


}
