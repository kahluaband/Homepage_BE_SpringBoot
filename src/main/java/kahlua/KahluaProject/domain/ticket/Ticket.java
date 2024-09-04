package kahlua.KahluaProject.domain.ticket;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import static kahlua.KahluaProject.domain.ticket.Type.FRESHMAN;
import static kahlua.KahluaProject.domain.ticket.Type.GENERAL;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Long id;

    @Column(nullable = false)
    private String buyer; // 구매자 이름

    @Column(nullable = false)
    private String phone_num; // 구매자 번호

    private String reservationId; // 예약 번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;  // 티켓 유형 : 신입생 혹은 일반

    private String major; // 학과 (신입생)

    @Column(unique = true)
    private String studentId; // 학번 (신입생)

    @Enumerated(EnumType.STRING)
    private Meeting meeting; // 가능한 뒷풀이 참석 날짜 (신입생)

    @Enumerated(EnumType.STRING)
    private Status status;  // 결제 상태 (대기, 종료, 취소 요청, 취소 완료)

    private String email; // 구매자 이메일 (결제 완료 메일 발송에 사용)

    @PrePersist
    public void onCreate() {
        if (status == null && type == GENERAL) {
            status = Status.WAIT;  // default 값 설정
        }
        else if (status == null && type == FRESHMAN) {
            status = Status.FINISH_PAYMENT;  // 신입생의 경우 바로 FINISH
        }
    }

    public void completePayment() { this.status = Status.FINISH_PAYMENT; }

    public void requestCancelTicket() {
        this.status = Status.CANCEL_REQUEST;
    }

    public void completeCancel() {
        this.status = Status.CANCEL_COMPLETE;
    }
}
