package kahlua.KahluaProject.repository.kahluaInfo;

import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaderInfoRepository extends JpaRepository<LeaderInfo, Long> {
    Optional<LeaderInfo> findTopByOrderByIdDesc();
}