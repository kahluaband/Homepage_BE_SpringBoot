package kahlua.KahluaProject.global.config;

import kahlua.KahluaProject.global.websocket.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler; // STOMP 메세지 입출력 담당

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // Web Socket 시작 endpoint 등록
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:8080", "http://kahluaband.com", "https://kahluaband.com", "http://localhost:3000",
                        "https://www.kahluaband.com/", "http://www.kahluaband.com/", "https://kahluabandver20-caminobelllos-projects.vercel.app",
                        "https://api.kahluaband.com/", "http://api.kahluaband.com/","https://jiangxy.github.io/websocket-debug-tool")
                .withSockJS(); // Web Socket 지원하지 않는 브라우저에서도 웹 소켓 사용 가능
                               // ws, wss 대신 http, https를 통해 웹 소켓 연결하도록 함
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) { // 메세지 브로커 설정
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 서버로 전송하는 메세지의 경로 앞에 /app 붙임
        registry.enableSimpleBroker("/topic"); // 메세지 브로커가 /topic 경로로 시작하는 메세지를 구독한 클라이언트들에게 보냄
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) { // 클라이언트로부터 들어오는 Web Socket 메세지 인터셉트
        registration.interceptors(stompHandler);
    }
}