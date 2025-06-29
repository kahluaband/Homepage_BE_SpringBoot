package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.performance.Performance;
import kahlua.KahluaProject.domain.performance.PerformanceStatus;
import kahlua.KahluaProject.dto.performance.request.PerformanceRequest;
import kahlua.KahluaProject.dto.performance.response.PerformanceListResponse;
import kahlua.KahluaProject.dto.performance.response.PerformanceResponse;
import kahlua.KahluaProject.vo.PerformanceData;

public class PerformanceConverter {

    public static PerformanceListResponse.performanceDto toPerformanceSummaryDto(Performance info, PerformanceStatus status){
        return PerformanceListResponse.performanceDto.builder()
                .ticketInfoId(info.getId())
                .title(info.getPerformanceData().title())
                .content(info.getPerformanceData().content())
                .status(status)
                .posterUrl(info.getPosterImageUrl())
                .build();
    }

    public static PerformanceData toPerformance(PerformanceRequest performanceRequest) {
        return PerformanceData.builder()
                .title(performanceRequest.title())
                .content(performanceRequest.content())
                .venue(performanceRequest.venue())
                .address(performanceRequest.address())
                .performanceStartTime(performanceRequest.performanceStartTime())
                .performanceEndTime(performanceRequest.performanceEndTime())
                .entranceTime(performanceRequest.entranceTime())
                .freshmanPrice(performanceRequest.freshmanPrice())
                .freshmanMaxPurchase(performanceRequest.freshmanMaxPurchase())
                .generalPrice(performanceRequest.generalPrice())
                .generalMaxPurchase(performanceRequest.generalMaxPurchase())
                .bookingStartDate(performanceRequest.bookingStartDate())
                .bookingEndDate(performanceRequest.bookingEndDate())
                .build();
    }

    public static PerformanceResponse toPerformanceDto(Performance performance) {
        return PerformanceResponse.builder()
                .id(performance.getId())
                .posterImageUrl(performance.getPosterImageUrl())
                .youtubeUrl(performance.getYoutubeUrl())
                .title(performance.getPerformanceData().title())
                .content(performance.getPerformanceData().content())
                .venue(performance.getPerformanceData().venue())
                .address(performance.getPerformanceData().address())
                .performanceStartTime(performance.getPerformanceData().performanceStartTime())
                .performanceEndTime(performance.getPerformanceData().performanceEndTime())
                .entranceTime(performance.getPerformanceData().entranceTime())
                .freshmanPrice(performance.getPerformanceData().freshmanPrice())
                .freshmanMaxPurchase(performance.getPerformanceData().freshmanMaxPurchase())
                .generalPrice(performance.getPerformanceData().generalPrice())
                .generalMaxPurchase(performance.getPerformanceData().generalMaxPurchase())
                .bookingStartDate(performance.getPerformanceData().bookingStartDate())
                .bookingEndDate(performance.getPerformanceData().bookingEndDate())
                .build();
    }
}
