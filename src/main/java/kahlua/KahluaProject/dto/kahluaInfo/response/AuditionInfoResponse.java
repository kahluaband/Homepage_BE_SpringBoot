package kahlua.KahluaProject.dto.kahluaInfo.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record AuditionInfoResponse (
    LocalDate documentStartDate,
    LocalDateTime documentDeadline,
    LocalDate vocalVideoDeadline,
    LocalDateTime auditionDateTime,
    LocalDate announcementDate
) {}
