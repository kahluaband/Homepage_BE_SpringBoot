package kahlua.KahluaProject.security.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import kahlua.KahluaProject.security.kakao.dto.KakaoProfile;
import kahlua.KahluaProject.security.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class KakaoClient {

    private static final Logger log = LoggerFactory.getLogger(KakaoClient.class);

    @Value("${kakao.client_id}")
    private String clientId;

    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com/v2/user/me";

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper;

    public KakaoToken getAccessTokenFromKakao(String code) {
        String response = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("kauth.kakao.com")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(response, KakaoToken.class);
        } catch (IOException e) {
            log.error("Error parsing KakaoToken: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Kakao access token", e);
        }
    }

    public KakaoProfile getMemberInfo(KakaoToken kakaoToken) {
        String response = restClient.get()
                .uri(KAUTH_USER_URL_HOST)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoToken.accessToken())
                .retrieve()
                .body(String.class);

        try {
            KakaoProfile kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);
            log.info("Received KakaoProfile: {}", kakaoProfile);
            return kakaoProfile;
        } catch (IOException e) {
            log.error("Error parsing KakaoProfile: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Kakao user profile", e);
        }
    }
}