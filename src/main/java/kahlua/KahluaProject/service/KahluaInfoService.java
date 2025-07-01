package kahlua.KahluaProject.service;

import kahlua.KahluaProject.converter.KahluaInfoConverter;
import kahlua.KahluaProject.domain.kahluaInfo.AuditionInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.dto.kahluaInfo.request.AuditionInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.AuditionInfoResponse;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;
import kahlua.KahluaProject.global.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.global.exception.GeneralException;
import kahlua.KahluaProject.repository.kahluaInfo.AuditionInfoRepository;
import kahlua.KahluaProject.repository.kahluaInfo.LeaderInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class KahluaInfoService {
    private final LeaderInfoRepository leaderInfoRepository;
    private final AuditionInfoRepository auditionInfoRepository;
    private final MailCacheService mailCacheService;

    @Transactional
    public LeaderInfoResponse updateLeaderInfo(LeaderInfoRequest request, User user) {
        if (user.getUserType() != UserType.ADMIN) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        LeaderInfo info = getOrCreateLeaderInfo();
        LeaderInfo newInfo = KahluaInfoConverter.toLeaderInfo(request);

        info.update(newInfo);
        mailCacheService.updateLeaderInfo(info);
        return KahluaInfoConverter.toLeaderDto(info);
    }

    @Transactional
    public AuditionInfoResponse updateAuditionInfo(AuditionInfoRequest request, User user) {
        if (user.getUserType() != UserType.ADMIN) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        AuditionInfo info = getOrCreateAuditionInfo();
        AuditionInfo newInfo = KahluaInfoConverter.toAuditionInfo(request);

        info.update(newInfo);
        mailCacheService.updateAuditionInfo(info);
        return KahluaInfoConverter.toAuditionDto(info);
    }

    public LeaderInfoResponse getLeaderInfo() {
        LeaderInfo info = getOrCreateLeaderInfo();
        return KahluaInfoConverter.toLeaderDto(info);
    }

    public AuditionInfoResponse getAuditionInfo() {
        AuditionInfo info = getOrCreateAuditionInfo();
        return KahluaInfoConverter.toAuditionDto(info);
    }

    private LeaderInfo getOrCreateLeaderInfo() {
        return leaderInfoRepository.findTopByOrderByIdDesc()
                .orElseGet(() -> leaderInfoRepository.save(
                        KahluaInfoConverter.createEmptyLeaderInfo()
                ));
    }

    private AuditionInfo getOrCreateAuditionInfo() {
        return auditionInfoRepository.findTopByOrderByIdDesc()
                .orElseGet(() -> auditionInfoRepository.save(
                        KahluaInfoConverter.createEmptyAuditionInfo()
                ));
    }
}
