package kahlua.KahluaProject.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.apply.Preference;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.apply.response.ApplyAdminGetResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyListResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.ApplyService;
import kahlua.KahluaProject.service.ExcelConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "관리자(지원하기)", description = "관리자(지원하기) 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/apply")
public class AdminApplyController {

    private final ApplyService applyService;
    private final ExcelConvertService excelConvertService;

    @GetMapping("/all")
    @Operation(summary = "지원자 리스트 조회", description = "id 기준으로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyList(@AuthenticationPrincipal AuthDetails authDetails) {
        ApplyListResponse applyListResponse = applyService.getApplyList(authDetails.user());
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/{applyId}")
    @Operation(summary = "지원자 상세정보 조회", description = "지원자 상세정보를 조회합니다")
    public ApiResponse<ApplyAdminGetResponse> getApplyDetail(@PathVariable Long applyId, @AuthenticationPrincipal AuthDetails authDetails) {
        if(authDetails.getUser().getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        ApplyAdminGetResponse applyAdminGetResponse = applyService.getApplyAdmin(applyId);
        return ApiResponse.onSuccess(applyAdminGetResponse);
    }

    @GetMapping
    @Operation(summary = "지원자 리스트 세션별 조회", description = "1지망 -> 2지망 악기 순서로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyListByPreference(@AuthenticationPrincipal AuthDetails authDetails, @RequestParam(name = "preference") Preference preference) {
        ApplyListResponse applyListResponse = applyService.getApplyListByPreference(authDetails.user(), preference);
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/download")
    public void applyListToExcel(HttpServletResponse response) throws IOException {
        excelConvertService.applyListToExcel(response);
    }
}
