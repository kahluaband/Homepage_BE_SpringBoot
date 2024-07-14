package kahlua.KahluaProject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    GENERAL("일반 사용자"),
    ADMIN("관리자");

    private final String role;
}
