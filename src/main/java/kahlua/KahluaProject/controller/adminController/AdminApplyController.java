package kahlua.KahluaProject.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.global.aop.checkAdmin.CheckUserType;
import kahlua.KahluaProject.global.apipayload.ApiResponse;
import kahlua.KahluaProject.global.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.apply.Preference;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.apply.response.ApplyAdminGetResponse;
import kahlua.KahluaProject.dto.applyInfo.request.ApplyInfoRequest;
import kahlua.KahluaProject.dto.applyInfo.response.ApplyInfoResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyListResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyStatisticsResponse;
import kahlua.KahluaProject.global.exception.GeneralException;
import kahlua.KahluaProject.global.security.AuthDetails;
import kahlua.KahluaProject.service.ApplyService;
import kahlua.KahluaProject.service.ExcelConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
@Tag(name = "관리자(지원하기)", description = "관리자(지원하기) 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/apply")
public class AdminApplyController {

    private final ApplyService applyService;
    private final ExcelConvertService excelConvertService;

    @CheckUserType(userType = UserType.ADMIN)
    @GetMapping("/all")
    @Operation(summary = "지원자 리스트 조회", description = "id 기준으로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyList(@AuthenticationPrincipal AuthDetails authDetails) {
        ApplyListResponse applyListResponse = applyService.getApplyList();
        return ApiResponse.onSuccess(applyListResponse);
    }

    @CheckUserType(userType = UserType.ADMIN)
    @GetMapping("/{applyId}")
    @Operation(summary = "지원자 상세정보 조회", description = "지원자 상세정보를 조회합니다")
    public ApiResponse<ApplyAdminGetResponse> getApplyDetail(@PathVariable Long applyId, @AuthenticationPrincipal AuthDetails authDetails) {
        if(authDetails.user().getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        ApplyAdminGetResponse applyAdminGetResponse = applyService.getApplyAdmin(applyId);
        return ApiResponse.onSuccess(applyAdminGetResponse);
    }

    @CheckUserType(userType = UserType.ADMIN)
    @GetMapping
    @Operation(summary = "지원자 리스트 세션별 조회", description = "1지망 -> 2지망 악기 순서로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyListByPreference(@AuthenticationPrincipal AuthDetails authDetails, @RequestParam(name = "preference") Preference preference) {
        ApplyListResponse applyListResponse = applyService.getApplyListByPreference(preference);
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/download")
    @Operation(summary = "지원자 리스트 엑셀 변환", description = "전체 지원자 리스트를 엑셀 파일로 변환하여 다운로드합니다.")
    public ResponseEntity<InputStreamResource> applyListToExcel() throws IOException {
        ByteArrayInputStream in = excelConvertService.applyListToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=applicants.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @CheckUserType(userType = UserType.ADMIN)
    @PutMapping("/info/{apply_info_id}")
    @Operation(summary = "지원 정보 수정", description = "지원 정보를 수정합니다")
    public ApiResponse<ApplyInfoResponse> updateApplyInfo(@PathVariable("apply_info_id") Long applyId, @RequestBody ApplyInfoRequest applyInfoRequest, @AuthenticationPrincipal AuthDetails authDetails) {
        return ApiResponse.onSuccess(applyService.updateApplyInfo(applyId, applyInfoRequest));
    }

    @GetMapping("/info/{apply_info_id}")
    @Operation(summary = "지원 정보 조회", description = "지원 정보를 조회합니다")
    public ApiResponse<ApplyInfoResponse> getApplyInfo(@PathVariable("apply_info_id") Long applyId) {
        return ApiResponse.onSuccess(applyService.getApplyInfo(applyId));
    }

    @CheckUserType(userType = UserType.ADMIN)
    @GetMapping("/statistics")
    @Operation(summary = "지원자 통계 조회", description = "지원자 통계를 조회합니다")
    public ApiResponse<ApplyStatisticsResponse> getApplyStatistics(@AuthenticationPrincipal AuthDetails authDetails) {
        return ApiResponse.onSuccess(applyService.getApplyStatistics());
    }
}

