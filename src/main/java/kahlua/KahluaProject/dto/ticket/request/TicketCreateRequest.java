package kahlua.KahluaProject.dto.ticket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kahlua.KahluaProject.domain.ticket.Meeting;
import kahlua.KahluaProject.domain.ticket.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateRequest {

    @Schema(description = "구매자 이름")
    private String buyer;

    @Schema(description = "구매자 전화번호")
    private String phone_num;

    @Schema(description = "티켓 종류")
    private Type type;

    @Schema(description = "전공")
    private String major;

    @Schema(description = "학번")
    private String studentId;

    @Schema(description = "뒷풀이 참석 여부")
    private Meeting meeting;

    @Schema(description = "참석자 정보")
    private List<ParticipantsCreateRequest> members;

    @Schema(description = "구매자 이메일")
    private String email;
}
