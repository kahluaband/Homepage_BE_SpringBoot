package kahlua.KahluaProject.repository.reservation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kahlua.KahluaProject.domain.reservation.Reservation;
import kahlua.KahluaProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static kahlua.KahluaProject.domain.reservation.QReservation.reservation;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Reservation> findByDate(LocalDate date) {

        return jpaQueryFactory
                .selectFrom(reservation)
                .where(reservation.reservationDate.eq(date))
                .orderBy(reservation.startTime.asc())
                .fetch();
    }

    @Override
    public List<Reservation> findByUser(User user) {

        return jpaQueryFactory
                .selectFrom(reservation)
                .where(reservation.user.eq(user))
                .orderBy(reservation.reservationDate.asc(), reservation.startTime.asc())
                .fetch();
    }
}
