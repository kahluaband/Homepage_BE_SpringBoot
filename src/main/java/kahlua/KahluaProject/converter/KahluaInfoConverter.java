package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.kahluaInfo.AuditionInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.dto.kahluaInfo.request.AuditionInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.AuditionInfoResponse;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class KahluaInfoConverter {

    public static LeaderInfo toLeaderInfo(LeaderInfoRequest request) {
        return LeaderInfo.builder()
                .leaderName(request.leaderName())
                .leaderPhoneNum(request.phoneNumber())
                .leaderEmail(request.email())
                .build();
    }

    public static LeaderInfoResponse toLeaderDto(LeaderInfo info) {
        return LeaderInfoResponse.builder()
                .leaderName(info.getLeaderName())
                .phoneNumber(info.getLeaderPhoneNum())
                .email(info.getLeaderEmail())
                .build();
    }

    public static LeaderInfo createEmptyLeaderInfo() {
        return LeaderInfo.builder()
                .leaderName("")
                .leaderPhoneNum("")
                .leaderEmail("")
                .build();
    }

    public static AuditionInfo toAuditionInfo(AuditionInfoRequest request) {
        return AuditionInfo.builder()
                .documentStartDate(request.documentStartDate())
                .documentDeadline(request.documentDeadline())
                .vocalVideoDeadline(request.vocalVideoDeadline())
                .auditionDateTime(request.auditionDateTime())
                .announcementDate(request.announcementDate())
                .build();
    }

    public static AuditionInfoResponse toAuditionDto(AuditionInfo info) {
        return AuditionInfoResponse.builder()
                .documentStartDate(info.getDocumentStartDate())
                .documentDeadline(info.getDocumentDeadline())
                .vocalVideoDeadline(info.getVocalVideoDeadline())
                .auditionDateTime(info.getAuditionDateTime())
                .announcementDate(info.getAnnouncementDate())
                .build();
    }

    public static AuditionInfo createEmptyAuditionInfo() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        return AuditionInfo.builder()
                .documentStartDate(today)
                .documentDeadline(now)
                .vocalVideoDeadline(today)
                .auditionDateTime(now)
                .announcementDate(today)
                .build();
    }
}
