package kahlua.KahluaProject.dto.kahluaInfo.response;

import lombok.Builder;


@Builder
public record LeaderInfoResponse (
        String leaderName,
        String phoneNumber,
        String email
) {}