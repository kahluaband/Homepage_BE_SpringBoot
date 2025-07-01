package kahlua.KahluaProject.repository.kahluaInfo;

import kahlua.KahluaProject.domain.kahluaInfo.AuditionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditionInfoRepository extends JpaRepository<AuditionInfo, Long> {
    Optional<AuditionInfo> findTopByOrderByIdDesc();
}