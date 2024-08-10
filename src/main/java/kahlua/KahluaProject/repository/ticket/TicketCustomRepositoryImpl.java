package kahlua.KahluaProject.repository.ticket;


import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kahlua.KahluaProject.domain.ticket.Status;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kahlua.KahluaProject.domain.ticket.QTicket.ticket;

@Repository
@RequiredArgsConstructor
public class TicketCustomRepositoryImpl implements TicketCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Ticket> findAllOrderByStatus() {
        NumberExpression<Integer> order = new CaseBuilder()
                .when(ticket.status.eq(Status.CANCEL_REQUEST)).then(0)
                .when(ticket.status.eq(Status.WAIT)).then(1)
                .when(ticket.status.eq(Status.FINISH_PAYMENT)).then(2)
                .when(ticket.status.eq(Status.CANCEL_COMPLETE)).then(3)
                .otherwise(4);

        return jpaQueryFactory
                .selectFrom(ticket)
                .orderBy(order.asc(), ticket.id.desc())
                .fetch();
    }

    @Override
    public List<Ticket> findAllByTypeOrderByStatus(Type type) {
        NumberExpression<Integer> order = new CaseBuilder()
                .when(ticket.status.eq(Status.CANCEL_REQUEST)).then(0)
                .when(ticket.status.eq(Status.WAIT)).then(1)
                .when(ticket.status.eq(Status.FINISH_PAYMENT)).then(2)
                .when(ticket.status.eq(Status.CANCEL_COMPLETE)).then(3)
                .otherwise(4);

        return jpaQueryFactory
                .selectFrom(ticket)
                .where(ticket.type.eq(type))
                .orderBy(order.asc(), ticket.id.desc())
                .fetch();
    }
}
