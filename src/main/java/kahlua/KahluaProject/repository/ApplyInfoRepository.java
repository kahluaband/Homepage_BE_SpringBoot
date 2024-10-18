package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.applyInfo.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Long> {
}
