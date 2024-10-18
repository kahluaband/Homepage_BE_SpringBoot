package kahlua.KahluaProject.apipayload.code.status;

import kahlua.KahluaProject.apipayload.code.BaseCode;
import kahlua.KahluaProject.apipayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    // 에러 응답 (둘 중 하나 삭제)
    INTERNER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "서버 에러"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 에러"),

    // 로그인 실패
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "LOGIN FAIL", "아이디 또는 비밀번호를 확인하세요"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND", "사용자를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "금지된 접근입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "권한이 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.NOT_FOUND, "PASSWORD NOT MATCH", "비밀번호가 틀렸습니다."),
    PASSWORD_INVALID(HttpStatus.NOT_FOUND, "PASSWORD INVALID", "유효하지 않은 password입니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "TOKEN INVALID", "토큰 유효하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN INVALID", "토큰을 찾을 수 없습니다."),
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "ALREADY EXIST USER", "이미 존재하는 회원입니다."),
    REDIS_NOT_FOUND(HttpStatus.NOT_FOUND, "REDIS NOT FOUND", "Redis 설정에 오류가 발생했습니다."),


    // 세션 에러
    SESSION_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "유효하지 않은 세션입니다."),

    // 티켓 에러
    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND, "TICKET NOT FOUND", "티켓을 찾을 수 없습니다."),
    TICKET_COLUMN_INVALID(HttpStatus.BAD_REQUEST, "TICKET COLUMN INVALID", "올바르지 않은 티켓 속성입니다."),
    ALREADY_EXIST_STUDENT_ID(HttpStatus.BAD_REQUEST, "ALREADY EXIST STUDENT_ID", "이미 존재하는 학번입니다."),

    //지원하기 에러
    ALREADY_EXIST_APPLICANT(HttpStatus.BAD_REQUEST, "ALREADY EXIST APPLICANT", "이미 존재하는 지원자입니다."),
    APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICANT NOT FOUND", "존재하지 않는 지원자입니다."),
    APPLY_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLY INFO NOT FOUND", "존재하지 않는 지원정보 입니다." );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }

}