package kahlua.KahluaProject.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ApplyInfoData(
        @Schema(description = "기수", example = "23")
        String term,

        @Schema(description = "모집 시작일", example = "2024-03-01T00:00:00")
        LocalDateTime startDate,

        @Schema(description = "모집 마감일", example = "2024-03-10T23:59:59")
        LocalDateTime endDate,

        @Schema(description = "보컬 모집 마감일", example = "2024-03-11T23:59:59")
        LocalDateTime vocalEndDate,

        @Schema(description = "서류 지원 마감일", example = "2024-03-10T23:59:59")
        LocalDateTime applicationDeadline,

        @Schema(description = "서류 지원 설명")
        String applicationDescription,

        @Schema(description = "면접 날짜 일정", example = "2024-03-15T18:00:00")
        LocalDateTime interviewSchedule,

        @Schema(description = "면접 설명")
        String interviewDescription,

        @Schema(description = "뒷풀이", example = "2024-03-15T20:00:00")
        LocalDateTime afterParty,

        @Schema(description = "최종 합격 발표 일정", example = "2024-03-20")
        LocalDate finalAnnouncementSchedule,

        @Schema(description = "최종 합격 발표 설명")
        String finalAnnouncementDescription,

        @Schema(description = "활동 일정", example = "2025-03-25")
        LocalDate activitySchedule,

        @Schema(description = "활동 설명")
        String activityDescription
) {}
