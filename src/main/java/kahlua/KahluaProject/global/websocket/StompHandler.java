package kahlua.KahluaProject.global.websocket;

import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.repository.UserRepository;
import kahlua.KahluaProject.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    public static final String DEFAULT_PATH = "/topic/public/";

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository; // RestControllerAdvice 사용하지 않기 위해 userService 대신 userRepository 주입

    // WebSocket으로 들어온 메시지가 처리되기 전에 실행됨
    // STOMP 메시지의 종류에 따라 다른 작업을 수행하며, 메시지를 가로채 필요한 처리 가능
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message); // 메세지에서 헤더 정보를 쉽게 접근할 수 있도록 wrap
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) { // 클라이언트가 WebSocket 연결 요청
            // JWT 인증
            User user = getUserByAuthorizationHeader(
                    accessor.getFirstNativeHeader("Authorization"));

            // 인증 후 데이터를 헤더에 추가
            setValue(accessor, "email", user.getEmail());

        } else if (StompCommand.SUBSCRIBE.equals(command)) { // 예약 날짜 진입(구독 요청)

            String email = (String)getValue(accessor, "email");
            String reservationDate = parseReservationDateFromPath(accessor);
            log.debug("email : " + email + "reservationDate : " + reservationDate);
            setValue(accessor, "reservationDate", reservationDate);

        } else if (StompCommand.DISCONNECT == command) { // Websocket 연결 종료
            String email = (String)getValue(accessor, "email");
            log.info("DISCONNECTED email : {}", email);
        }

        log.info("header : " + message.getHeaders());
        log.info("message:" + message);

        return message;
    }

    private User getUserByAuthorizationHeader(String authHeaderValue) {
        String accessToken = getTokenByAuthorizationHeader(authHeaderValue);

        String email = jwtProvider.getEmail(accessToken);

        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new WebSocketException("사용자를 찾을 수 없습니다."));
    }

    private String getTokenByAuthorizationHeader(String authHeaderValue) {
        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new WebSocketException("authHeaderValue: " + authHeaderValue);
        }

        String accessToken = jwtProvider.resolveAccessTokenFromHeader(authHeaderValue);
        jwtProvider.validateToken(accessToken, "access");

        return accessToken;
    }

    // 메세지의 목적지 경로에서 예약 날짜 추출
    private String parseReservationDateFromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();

        if (Objects.isNull(destination) || destination.isBlank()) {
            throw new WebSocketException("destination: " + destination);
        }

        return destination.substring(DEFAULT_PATH.length());
    }

    // Stomp 세션 속성으로부터 원하는 값 추출
    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);

        if (Objects.isNull(value)) {
            throw new WebSocketException(key + " 에 해당하는 값이 없습니다.");
        }

        return value;
    }

    // Stomp 세션 속성에 원하는 값 삽입
    private void setValue(StompHeaderAccessor accessor, String key, Object value) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.put(key, value);
    }

    // Stomp 세션 속성 가져오기
    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        if (Objects.isNull(sessionAttributes)) {
            throw new WebSocketException("SessionAttributes가 null입니다.");
        }
        return sessionAttributes;
    }
}
