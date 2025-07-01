package kahlua.KahluaProject.dto.performance.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PerformanceRequest(
        @Schema(description = "공연 포스터 이미지", example = "https://kahlua.com/1.jpg")
        String posterImageUrl,

        @Schema(description = "공연 영성 링크", example = "https://youtu.be/QaGbEsecSVE?si=2g2krmBXw1qQZcaW")
        String youtubeUrl,

        @Schema(description = "공연 제목", example = "2024년 3월 정기 공연")
        String title,

        @Schema(description = "공연 설명", example="#스물다섯_스물하나 #데이식스 #잔나비 #YB밴드 #백예린 #미도와_파라솔")
        String content,

        @Schema(description = "장소", example = "001 클럽")
        String venue,

        @Schema(description = "주소", example = "서울 마포구 월드컵북로2길 49")
        String address,

        @Schema(description = "시작 시각", example = "2024-03-02T19:00:00")
        LocalDateTime performanceStartTime,

        @Schema(description = "종료 시각", example = "2024-03-02T19:00:00")
        LocalDateTime performanceEndTime,

        @Schema(description = "입장 시각", example = "2024-03-02T19:00:00")
        LocalDateTime entranceTime,

        @Schema(description = "신입생 가격", example = "0")
        String freshmanPrice,

        @Schema(description = "신입생 최대 구매 개수", example = "1")
        int freshmanMaxPurchase,

        @Schema(description = "일반 가격", example = "5000")
        String generalPrice,

        @Schema(description = "일반 최대 구매 개수", example = "5")
        int generalMaxPurchase,

        @Schema(description = "예매 시작 날짜", example = "2024-02-20T10:00:00")
        LocalDateTime bookingStartDate,

        @Schema(description = "예매 마감 날짜", example = "2024-03-01T23:59:59")
        LocalDateTime bookingEndDate
) {
}
