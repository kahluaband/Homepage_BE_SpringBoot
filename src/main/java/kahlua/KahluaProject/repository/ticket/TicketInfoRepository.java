package kahlua.KahluaProject.repository.ticket;

import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
}
