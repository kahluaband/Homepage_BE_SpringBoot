package kahlua.KahluaProject.dto.kahluaInfo.request;

import lombok.Builder;

@Builder
public record LeaderInfoRequest(
        String leaderName,
        String phoneNumber,
        String email
) {}