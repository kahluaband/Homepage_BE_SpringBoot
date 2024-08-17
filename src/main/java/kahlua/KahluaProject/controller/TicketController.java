package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import kahlua.KahluaProject.dto.ticket.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.ticket.response.TicketCreateResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketGetResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketUpdateResponse;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @Operation(summary = "공연 티켓 예매",
            description = "예매자, 참석자 정보를 입력하여 티켓을 예매합니다. </br> 일반 티켓인 경우 예매 후 티켓 상태는 WAIT이 되고, 신입생 티켓인 경우 티켓 상태는 FINISH가 됩니다.")
    public ResponseEntity<TicketCreateResponse> createTicketForm(@RequestBody TicketCreateRequest ticketCreateRequest) {
        TicketCreateResponse ticketCreateResponse = ticketService.createTicket(ticketCreateRequest);
        return ResponseEntity.ok(ticketCreateResponse);
    }

    @GetMapping("/get")
    @Operation(summary = "예매한 티켓 조회", description = "예약번호를 입력하면 일치하는 티켓을 불러옵니다.")
    public ResponseEntity<TicketGetResponse> viewTicketForm(@RequestParam(name = "reservationId") String reservationId) {
        TicketGetResponse ticketGetResponse = ticketService.viewTicket(reservationId);
        return ResponseEntity.ok(ticketGetResponse);
    }

    @PatchMapping("/cancel-request")
    @Operation(summary = "티켓 취소 요청", description = "예약번호로 티켓 확인을 한 뒤, 티켓 취소를 클릭하면 티켓 취소 요청으로 상태가 변합니다.")
    public ResponseEntity<TicketUpdateResponse> requestCancelForm(@RequestParam(name = "reservationId") String reservationId) {
        TicketUpdateResponse ticketUpdateResponse = ticketService.requestCancelTicket(reservationId);
        return ResponseEntity.ok(ticketUpdateResponse);
    }
}
