package kahlua.KahluaProject.global.websocket;

import kahlua.KahluaProject.dto.reservation.request.SubRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    // WebSocket 연결, 구독, 해제 event 로깅
    // + 구독자들에게 구독, 해제 메세지 전송 여부는 논의 필요

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;

    // 연결 요청
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
            logger.info("Received a new web socket connection");
    }

    // 구독 요청(입장)
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("Received a new web socket subscribe");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String email = (String)accessor.getSessionAttributes().get("email");
        String reservationDate = (String) accessor.getSessionAttributes().get("reservationDate");

        if (email == null) {
            logger.warn("SUBSCRIBE 처리 생략: 세션에 email 정보가 없습니다.");
            return;
        }
        if (reservationDate == null || reservationDate.isBlank()) {
            logger.warn("SUBSCRIBE 처리 생략: 헤더에 reservationDate가 없습니다.");
            return;
        }

        logger.info("User: {} Subscribe ReservationDate : {}", email, reservationDate);

        SubRequest subRequest = SubRequest.builder()
                .type("JOIN")
                .email(email)
                .content(email + " 님이 입장했습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/public/" + reservationDate, subRequest);

    }

    // 연결 해제
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String email = (String)accessor.getSessionAttributes().get("email");
        String reservationDate = (String) accessor.getSessionAttributes().get("reservationDate");

        if (email == null) {
            logger.warn("DISCONNECT 처리 생략: 세션에 email 정보가 없습니다.");
            return;
        }
        if (reservationDate == null || reservationDate.isBlank()) {
            logger.warn("DISCONNECT 처리 생략: 헤더에 reservationDate가 없습니다.");
            return;
        }

        logger.info("User: {} Disconnected ReservationDate : {}", email, reservationDate);

        SubRequest subRequest = SubRequest.builder()
                .type("LEAVE")
                .email(email)
                .content(email + " 님이 떠났습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/public/" + reservationDate, subRequest);
    }
}