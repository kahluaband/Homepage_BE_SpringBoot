package kahlua.KahluaProject.dto.user.response;

import kahlua.KahluaProject.domain.user.*;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserListOneResponse {
    private Long id;
    private Long term;
    private String name;
    private Session session;
    private LoginType loginType;
    private UserType userType;
    private String approvalStatus;

    public static UserListOneResponse from(User user) {
        return UserListOneResponse.builder()
                .id(user.getId())
                .term(user.getTerm())
                .name(user.getName())
                .session(user.getSession())
                .loginType(user.getLoginType())
                .userType(user.getUserType())
                .approvalStatus(
                        user.getUserType() == UserType.PENDING ? "PENDING" :
                                (user.getUserType() == UserType.KAHLUA || user.getUserType() == UserType.ADMIN)
                                        ? "APPROVED" : "UNAPPROVED")
                .build();
    }
}
