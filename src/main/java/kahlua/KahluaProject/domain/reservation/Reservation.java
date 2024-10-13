package kahlua.KahluaProject.domain.reservation;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import kahlua.KahluaProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_type", nullable = false)
    private ReservationType type; // 개인, 팀 구분

    @Column(name = "club_room_username")
    private String clubRoomUsername; // 동방 예약자명(팀명)

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate; // 예약날짜

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // 사용 시작 시간

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime; // 사용 종료 시간

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus status; // 예약 상태 - 진행중, 예약됨, 취소됨
}
