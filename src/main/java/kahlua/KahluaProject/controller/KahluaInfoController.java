package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.kahluaInfo.request.AuditionInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.AuditionInfoResponse;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;
import kahlua.KahluaProject.global.aop.checkAdmin.CheckUserType;
import kahlua.KahluaProject.global.apipayload.ApiResponse;
import kahlua.KahluaProject.global.security.AuthDetails;
import kahlua.KahluaProject.service.KahluaInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/kahlua-info")
@Tag(name = "깔루아 기본정보", description = "담당자/오디션 관련 학회 정보 API")
public class KahluaInfoController {
    private final KahluaInfoService kahluaInfoService;

    @GetMapping("/leader")
    @Operation(summary = "담당자 정보 조회 API", description = "담당자 정보 조회")
    public ApiResponse<LeaderInfoResponse> getLeaderInfo() {
        LeaderInfoResponse response = kahluaInfoService.getLeaderInfo();
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/audition")
    @Operation(summary = "오디션 정보 조회 API", description = "오디션 정보 조회")
    public ApiResponse<AuditionInfoResponse> getAuditionInfo() {
        AuditionInfoResponse response = kahluaInfoService.getAuditionInfo();
        return ApiResponse.onSuccess(response);
    }

    @CheckUserType(userType = UserType.ADMIN)
    @PatchMapping("/leader/update")
    @Operation(summary = "담당자 정보 수정 API", description = "학회장 이름, 연락처, 이메일 수정")
    public ApiResponse<LeaderInfoResponse> updateLeaderInfo(@RequestBody LeaderInfoRequest request, @AuthenticationPrincipal AuthDetails authDetails) {
        LeaderInfoResponse response = kahluaInfoService.updateLeaderInfo(request, authDetails.user());
        return ApiResponse.onSuccess(response);
    }

    @CheckUserType(userType = UserType.ADMIN)
    @PatchMapping("/audition/update")
    @Operation(summary = "오디션 정보 수정 API", description = "오디션 날짜 관련 정보 수정")
    public ApiResponse<AuditionInfoResponse> updateAuditionInfo(@RequestBody AuditionInfoRequest request, @AuthenticationPrincipal AuthDetails authDetails) {
        AuditionInfoResponse response = kahluaInfoService.updateAuditionInfo(request, authDetails.user());
        return ApiResponse.onSuccess(response);
    }
}