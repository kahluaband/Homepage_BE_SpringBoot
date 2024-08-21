package kahlua.KahluaProject.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketListResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketUpdateResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.ExcelConvertService;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "관리자(예매하기)", description = "관리자(예매하기) 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/tickets")
public class AdminTicketController {

    private final TicketService ticketService;
    private final ExcelConvertService excelConvertService;

    @GetMapping
    @Operation(summary = "전체 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 전체 티켓 리스트를 조회합니다 " +
            "</br> sort-by를 쿼리에 포함시키지 않은 경우 (환불 요청 -> 결제 대기 -> 결제 왼료 -> 환불 완료) 순서로, 같은 결제 상태에 대해서는 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                         @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/general")
    @Operation(summary = "일반 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 일반 티켓 리스트를 조회합니다 " +
            "</br> sort-by를 쿼리에 포함시키지 않은 경우 (환불 요청 -> 결제 대기 -> 결제 왼료 -> 환불 완료) 순서로, 같은 결제 상태에 대해서는 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getGeneralTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                                @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getGeneralTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/freshman")
    @Operation(summary = "신입생 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 신입생 티켓 리스트를 조회합니다 " +
            "</br> sort-by를 쿼리에 포함시키지 않은 경우 (환불 요청 -> 결제 대기 -> 결제 왼료 -> 환불 완료) 순서로, 같은 결제 상태에 대해서는 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getFreshmanTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                                 @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getFreshmanTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @PatchMapping("/{ticketId}/ticket-complete")
    @Operation(summary = "티켓 결제 완료", description = "티켓 결제를 완료한 뒤 어드민 페이지에서 결제 완료를 클릭하는 경우 결제 완료로 상태가 변합니다.")
    public ApiResponse<TicketUpdateResponse> completePaymentForm(@PathVariable(name = "ticketId") Long ticketId, @AuthenticationPrincipal AuthDetails authDetails) {
        TicketUpdateResponse ticketUpdateResponse = ticketService.completePayment(authDetails.user(), ticketId);
        return ApiResponse.onSuccess(ticketUpdateResponse);
    }

    @PatchMapping("/{ticketId}/cancel-complete")
    @Operation(summary = "티켓 취소 완료", description = "티켓 환불을 완료한 뒤 어드민 페이지에서 취소 완료를 클릭하는 경우 취소가 완료로 상태가 변합니다.")
    public ApiResponse<TicketUpdateResponse> completeCancelForm(@PathVariable(name = "ticketId") Long ticketId, @AuthenticationPrincipal AuthDetails authDetails) {
        TicketUpdateResponse ticketUpdateResponse = ticketService.completeCancelTicket(authDetails.user(), ticketId);
        return ApiResponse.onSuccess(ticketUpdateResponse);
    }

    @GetMapping("/download")
    public void applyListToExcel(HttpServletResponse response) throws IOException {
        excelConvertService.ticketListToExcel(response);
    }
}
