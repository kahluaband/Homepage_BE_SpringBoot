package kahlua.KahluaProject.dto.kahluaInfo.request;

import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record AuditionInfoRequest(
        LocalDate documentStartDate,
        LocalDateTime documentDeadline,
        LocalDate vocalVideoDeadline,
        LocalDateTime auditionDateTime,
        LocalDate announcementDate
) {}