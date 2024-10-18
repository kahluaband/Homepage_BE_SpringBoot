package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import kahlua.KahluaProject.dto.ticket.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.ticket.response.ParticipantsResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketCreateResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketGetResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketUpdateResponse;
import kahlua.KahluaProject.dto.ticketInfo.request.TicketInfoRequest;
import kahlua.KahluaProject.dto.ticketInfo.response.TicketInfoResponse;
import kahlua.KahluaProject.vo.TicketInfoData;

import java.util.List;
import java.util.stream.Collectors;

//@Component
public class TicketConverter {

    public static Ticket toTicket(TicketCreateRequest ticketCreateRequest, String reservationId) {
        Ticket ticket;
        ticket = Ticket.builder()
                .buyer(ticketCreateRequest.getBuyer())
                .phone_num(ticketCreateRequest.getPhone_num())
                .reservationId(reservationId)
                .type(ticketCreateRequest.getType())
                .major(ticketCreateRequest.getMajor())
                .studentId(ticketCreateRequest.getStudentId())
                .meeting(ticketCreateRequest.getMeeting())
                .email(ticketCreateRequest.getEmail())
                .build();

        return ticket;
    }

    public static TicketCreateResponse toTicketCreateResponse(Ticket ticket, String reservationId, List<Participants> members) {
        return TicketCreateResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservationId(reservationId)
                .type(ticket.getType())
                .major(ticket.getMajor())
                .studentId(ticket.getStudentId())
                .meeting(ticket.getMeeting())
                .members(members)
                .status(ticket.getStatus())
                .email(ticket.getEmail())
                .build();
    }

    public static TicketUpdateResponse toTicketUpdateResponse(Ticket ticket) {
        return TicketUpdateResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservationId(ticket.getReservationId())
                .type(ticket.getType())
                .major(ticket.getMajor())
                .studentId(ticket.getStudentId())
                .meeting(ticket.getMeeting())
                .status(ticket.getStatus())
                .build();
    }

    public static TicketGetResponse toTicketGetResponse(Ticket ticket, List<Participants> participants) {
        List<ParticipantsResponse> memberResponses = participants.stream()
                .map(member -> ParticipantsResponse.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .phone_num(member.getPhone_num())
                        .build())
                .collect(Collectors.toList());

        return TicketGetResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservationId(ticket.getReservationId())
                .type(ticket.getType())
                .major(ticket.getMajor())
                .studentId(ticket.getStudentId())
                .meeting(ticket.getMeeting())
                .members(memberResponses)
                .status(ticket.getStatus())
                .build();
    }

    public static TicketInfoData toTicketInfo(TicketInfoRequest ticketInfoRequest) {
        return TicketInfoData.builder()
                .title(ticketInfoRequest.title())
                .venue(ticketInfoRequest.venue())
                .address(ticketInfoRequest.address())
                .dateTime(ticketInfoRequest.dateTime())
                .freshmanPrice(ticketInfoRequest.freshmanPrice())
                .freshmanMaxPurchase(ticketInfoRequest.freshmanMaxPurchase())
                .generalPrice(ticketInfoRequest.generalPrice())
                .generalMaxPurchase(ticketInfoRequest.generalMaxPurchase())
                .bookingStartDate(ticketInfoRequest.bookingStartDate())
                .bookingEndDate(ticketInfoRequest.bookingEndDate())
                .build();
    }

    public static TicketInfoResponse toTicketInfoResponse(TicketInfo ticketInfo) {
        return TicketInfoResponse.builder()
                .id(ticketInfo.getId())
                .posterImageUrl(ticketInfo.getPosterImageUrl())
                .title(ticketInfo.getTicketInfoData().title())
                .venue(ticketInfo.getTicketInfoData().venue())
                .address(ticketInfo.getTicketInfoData().address())
                .dateTime(ticketInfo.getTicketInfoData().dateTime())
                .freshmanPrice(ticketInfo.getTicketInfoData().freshmanPrice())
                .freshmanMaxPurchase(ticketInfo.getTicketInfoData().freshmanMaxPurchase())
                .generalPrice(ticketInfo.getTicketInfoData().generalPrice())
                .generalMaxPurchase(ticketInfo.getTicketInfoData().generalMaxPurchase())
                .bookingStartDate(ticketInfo.getTicketInfoData().bookingStartDate())
                .bookingEndDate(ticketInfo.getTicketInfoData().bookingEndDate())
                .build();
    }
}
