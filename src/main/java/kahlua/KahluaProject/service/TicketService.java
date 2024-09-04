package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Status;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.ticket.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.ticket.response.*;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ParticipantsRepository;
import kahlua.KahluaProject.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParticipantsRepository participantsRepository;

    @Transactional
    public TicketCreateResponse createTicket(TicketCreateRequest ticketCreateRequest) {

        String reservationId = uniqueReservationId();

        Ticket savedTicket = TicketConverter.toTicket(ticketCreateRequest, reservationId);

        try {
            ticketRepository.save(savedTicket);
        } catch (DataIntegrityViolationException e) {  //SQLIntegrityConstraintViolationException 발생하는 경우
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_STUDENT_ID);
        }

        // service 혹은 converter
        List<Participants> members = ticketCreateRequest.getMembers().stream()
                .map(memberRequest -> Participants.builder()
                        .name(memberRequest.getName())
                        .phone_num(memberRequest.getPhone_num())
                        .ticket(savedTicket)
                        .build())
                .collect(Collectors.toList());

        participantsRepository.saveAll(members);

        TicketCreateResponse ticketCreateResponse = TicketConverter.toTicketCreateResponse(savedTicket, reservationId, members);
        return ticketCreateResponse;
    }

    //티켓 결제 완료한 경우
    @Transactional
    public TicketUpdateResponse completePayment(User user, Long ticketId) {

        if (user.getUserType() != UserType.ADMIN) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TICKET_NOT_FOUND));
        existingTicket.completePayment();

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        TicketUpdateResponse ticketUpdateResponse = TicketConverter.toTicketUpdateResponse(updatedTicket);
        return ticketUpdateResponse;

    }

    //티켓 취소 요청하는 경우
    @Transactional
    public TicketUpdateResponse requestCancelTicket(String reservationId) {

        Ticket existingTicket = ticketRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TICKET_NOT_FOUND));
        existingTicket.requestCancelTicket();

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        TicketUpdateResponse ticketUpdateResponse = TicketConverter.toTicketUpdateResponse(updatedTicket);
        return ticketUpdateResponse;
    }

    //티켓 취소 완료
    @Transactional
    public TicketUpdateResponse completeCancelTicket(User user, Long ticketId) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TICKET_NOT_FOUND));
        existingTicket.completeCancel();

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        TicketUpdateResponse ticketUpdateResponse = TicketConverter.toTicketUpdateResponse(updatedTicket);
        return ticketUpdateResponse;
    }

    @Transactional
    public TicketGetResponse viewTicket(String reservationId) {

        Ticket ticket = ticketRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TICKET_NOT_FOUND));

        List<Participants> participants = participantsRepository.findByTicket(ticket);

        return TicketConverter.toTicketGetResponse(ticket, participants);
    }

    // 어드민 페이지 티켓 리스트 조회
    public TicketListResponse getTicketList(User user, String sortBy) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets;

        if (sortBy != null) {
            // sortBy 값에 따라 티켓 속성 기준 정렬
            // 추후 정렬 기준 추가되면 case문에 추가
            tickets = switch (sortBy) {
                case "buyer" -> ticketRepository.findAllByOrderByBuyerAscIdDesc();
                // case "status"
                default -> throw new GeneralException(ErrorStatus.TICKET_COLUMN_INVALID);
            };
        }
        // sortBy 값이 없다면 결제 상태에 따라, 최신순으로 정렬
        else {
            tickets = ticketRepository.findAllOrderByStatus();
        }

        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {

            List<ParticipantsResponse> memberResponses = participantsRepository.findByTicket(ticket).stream()
                    .map(participant -> ParticipantsResponse.builder()
                            .id(participant.getId())
                            .name(participant.getName())
                            .phone_num(participant.getPhone_num())
                            .build())
                    .collect(Collectors.toList());

            // 일반티켓
            // 전공, 뒷풀이 참석 여부 null
            if (ticket.getType() == Type.GENERAL){
                TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                        .id(ticket.getId())
                        .status(ticket.getStatus())
                        .reservation_id(ticket.getReservationId())
                        .buyer(ticket.getBuyer())
                        .phone_num(ticket.getPhone_num())
                        .members(memberResponses)
                        .total_ticket(participantsRepository.countByTicket_Id(ticket.getId()) + 1)
                        .build();

                ticketItemResponses.add(ticketItemResponse);
            }
            // 신입생티켓
            // 티켓 매수 1 고정
            else if (ticket.getType() == Type.FRESHMAN) {
                TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                        .id(ticket.getId())
                        .status(ticket.getStatus())
                        .reservation_id(ticket.getReservationId())
                        .buyer(ticket.getBuyer())
                        .studentId(ticket.getStudentId())
                        .phone_num(ticket.getPhone_num())
                        .total_ticket(1)
                        .major(ticket.getMajor())
                        .meeting(ticket.getMeeting())
                        .build();

                ticketItemResponses.add(ticketItemResponse);
            }
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    // 일반 티켓 리스트 조회
    public TicketListResponse getGeneralTicketList(User user, String sortBy) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets;

        if (sortBy != null) {
            // sortBy 값에 따라 티켓 속성 기준 정렬
            tickets = switch (sortBy) {
                case "buyer" -> ticketRepository.findAllByTypeOrderByBuyerAscIdDesc(Type.GENERAL);
                default -> throw new GeneralException(ErrorStatus.TICKET_COLUMN_INVALID);
            };
        }
        // sortBy 값이 없다면 결제 상태에 따라, 최신순으로 정렬
        else {
            tickets = ticketRepository.findAllByTypeOrderByStatus(Type.GENERAL);
        }

        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {

            List<ParticipantsResponse> memberResponses = participantsRepository.findByTicket(ticket).stream()
                    .map(participant -> ParticipantsResponse.builder()
                            .id(participant.getId())
                            .name(participant.getName())
                            .phone_num(participant.getPhone_num())
                            .build())
                    .collect(Collectors.toList());

            TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                    .id(ticket.getId())
                    .status(ticket.getStatus())
                    .reservation_id(ticket.getReservationId())
                    .buyer(ticket.getBuyer())
                    .phone_num(ticket.getPhone_num())
                    .members(memberResponses)
                    .total_ticket(participantsRepository.countByTicket_Id(ticket.getId()) + 1)
                    .build();

            ticketItemResponses.add(ticketItemResponse);
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    // 신입생 티켓 리스트 조회
    public TicketListResponse getFreshmanTicketList(User user, String sortBy) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets;

        if (sortBy != null) {
            // sortBy 값에 따라 티켓 속성 기준 정렬
            tickets = switch (sortBy) {
                case "buyer" -> ticketRepository.findAllByTypeOrderByBuyerAscIdDesc(Type.FRESHMAN);
                default -> throw new GeneralException(ErrorStatus.TICKET_COLUMN_INVALID);
            };
        }
        // sortBy 값이 없다면 결제 상태에 따라, 최신순으로 정렬
        else {
            tickets = ticketRepository.findAllByTypeOrderByStatus(Type.FRESHMAN);
        }

        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                    .id(ticket.getId())
                    .status(ticket.getStatus())
                    .studentId(ticket.getStudentId())
                    .reservation_id(ticket.getReservationId())
                    .buyer(ticket.getBuyer())
                    .phone_num(ticket.getPhone_num())
                    .total_ticket(1)
                    .major(ticket.getMajor())
                    .studentId(ticket.getStudentId())
                    .meeting(ticket.getMeeting())
                    .build();

            ticketItemResponses.add(ticketItemResponse);
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    //예약번호가 기존 예약번호와 일치하면 안되므로 중복되는지 확인하는 기능
    public String uniqueReservationId() {
        String reservationId;
        do {
            reservationId = generateReservationId();
        } while(ticketRepository.existsByReservationId(reservationId));

        return reservationId;
    }

    public String generateReservationId() {
        int length = 10;  // 예약 번호 길이
        Random random = new Random();
        List<String> idList = new ArrayList<>();

        for (int i=0; i<length/2; i++) {
            idList.add(String.valueOf(random.nextInt(10)));
        }

        for (int i=0; i<length/2; i++) {
            idList.add(String.valueOf((char) (random.nextInt(26) + 65)));
        }

        Collections.shuffle(idList);

        return String.join("", idList);
    }

    // 티켓 매수 count - 티켓 리스트 조회에 사용
    public Integer countTickets(List<TicketItemResponse> ticketItemResponses) {

        Integer total = 0;
        for (TicketItemResponse ticketItemResponse : ticketItemResponses) {
            if (ticketItemResponse.getStatus() != Status.CANCEL_COMPLETE) {
                total += ticketItemResponse.getTotal_ticket();
            }
        }

        return total;
    }
}
