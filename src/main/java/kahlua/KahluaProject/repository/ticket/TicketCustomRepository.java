package kahlua.KahluaProject.repository.ticket;

import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;

import java.util.List;

public interface TicketCustomRepository {

    List<Ticket> findAllOrderByStatus();
    List<Ticket> findAllByTypeOrderByStatus(Type type);
}
