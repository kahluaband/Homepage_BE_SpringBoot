package kahlua.KahluaProject.global.security.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoToken(
    String tokenType,
    String accessToken,
    Integer expiresIn,
    String refreshToken,
    Integer refreshTokenExpiresIn,
    String scope
    ){}