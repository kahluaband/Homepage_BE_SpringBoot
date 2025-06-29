package kahlua.KahluaProject.domain.kahluaInfo;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuditionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audition_info_id")
    private Long id;

    @Column(name = "document_startDate") //서류지원 시작날짜
    private LocalDate documentStartDate;

    @Column(name = "document_deadline") //서류지원 마감날짜, 시각
    private LocalDateTime documentDeadline;

    @Column(name = "video_deadline")
    private LocalDate vocalVideoDeadline; //보컬 영상제출 날짜

    @Column(name = "audition_date")
    private LocalDateTime auditionDateTime; //오디션 날짜, 시각

    @Column(name = "announcement_date")
    private LocalDate announcementDate; //합격발표 날짜, 시각

    public void update(AuditionInfo auditionInfo) {
        this.documentStartDate = auditionInfo.documentStartDate;
        this.documentDeadline = auditionInfo.documentDeadline;
        this.vocalVideoDeadline = auditionInfo.vocalVideoDeadline;
        this.auditionDateTime = auditionInfo.auditionDateTime;
        this.announcementDate = auditionInfo.announcementDate;
    }
}
